package ru.dpohvar.varscript.boot;

import groovy.lang.*;
import org.codehaus.groovy.reflection.CachedClass;
import org.codehaus.groovy.runtime.m12n.ExtensionModule;
import org.codehaus.groovy.runtime.m12n.ExtensionModuleRegistry;
import org.codehaus.groovy.runtime.m12n.ExtensionModuleScanner;
import org.codehaus.groovy.runtime.metaclass.MetaClassRegistryImpl;
import org.codehaus.groovy.util.FastArray;
import ru.dpohvar.varscript.VarScript;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class BootHelper {

    public static void loadExtensions(ClassLoader classLoader){

        Map<CachedClass, List<MetaMethod>> map = new HashMap<>();
        MetaClassRegistryImpl registry = (MetaClassRegistryImpl) GroovySystem.getMetaClassRegistry();
        final ExtensionModuleRegistry moduleRegistry = registry.getModuleRegistry();
        final FastArray instanceMethods = registry.getInstanceMethods();
        final FastArray staticMethods = registry.getStaticMethods();
        new ExtensionModuleScanner(module -> {
            if (moduleRegistry.hasModule(module.getName())) {
                ExtensionModule loadedModule = moduleRegistry.getModule(module.getName());
                if (loadedModule.getVersion().equals(module.getVersion())) {
                    // already registered
                    return;
                } else {
                    throw new GroovyRuntimeException("Conflicting module versions. Module [" + module.getName() + " is loaded in version " +
                            loadedModule.getVersion() + " and you are trying to load version " + module.getVersion());
                }
            }
            moduleRegistry.addModule(module);
            // register MetaMethods
            List<MetaMethod> metaMethods = module.getMetaMethods();
            for (MetaMethod metaMethod : metaMethods) {
                CachedClass cachedClass = metaMethod.getDeclaringClass();
                List<MetaMethod> methods = map.computeIfAbsent(cachedClass, k -> new ArrayList<>(4));
                methods.add(metaMethod);
                if (metaMethod.isStatic()) {
                    staticMethods.add(metaMethod);
                } else {
                    instanceMethods.add(metaMethod);
                }
            }
        }, classLoader).scanClasspathModules();

        for (Map.Entry<CachedClass, List<MetaMethod>> e : map.entrySet()) {
            CachedClass cls = e.getKey();
            var expand = (ExpandoMetaClass) GroovySystem.getMetaClassRegistry().getMetaClass(cls.getTheClass());
            e.getValue().forEach(expand::registerInstanceMethod);
        }
    }

    /**
     * Scan class names in package
     * @param packageName name
     * @return class names in package
     */
    public static ArrayList<String> getClassNamesFromPackage(String packageName, boolean recursive) {
        ClassLoader resourceClassLoader = VarScript.class.getClassLoader();
        ArrayList<String> names = new ArrayList<String>();
        packageName = packageName.replace('.','/');
        if (!packageName.endsWith("/")) packageName += '/';

        var urlList = new LinkedList<URL>();

        var packageClassLoader = resourceClassLoader;
        try {
            while (packageClassLoader != null) {
                var resources = packageClassLoader.getResources(packageName);
                resources.asIterator().forEachRemaining(urlList::add);
                packageClassLoader = packageClassLoader.getParent();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        for (URL packageURL : urlList) {
            if(packageURL.getProtocol().equals("jar")) try {
                // build jar file name, then loop through zipped entries
                String jarFileName = URLDecoder.decode(packageURL.getFile(), StandardCharsets.UTF_8);
                jarFileName = jarFileName.substring(5,jarFileName.indexOf("!"));
                JarFile jf = new JarFile(jarFileName);
                Enumeration<JarEntry> jarEntries = jf.entries();
                while (jarEntries.hasMoreElements()){
                    JarEntry entry = jarEntries.nextElement();
                    if (entry.isDirectory()) continue;
                    String entryName = entry.getName();
                    if (!entryName.startsWith(packageName)) continue;
                    if (!entryName.endsWith(".class")) continue;
                    if (entryName.contains("$")) continue;
                    if (!recursive) {
                        String end = entryName.substring(packageName.length());
                        if (end.contains("/")) continue;
                    }
                    String name = entryName.substring(0,entryName.length()-6);
                    String className = name.replace('/','.');
                    names.add(className);
                }
            } catch (IOException ignored) {}

        }
        return names;
    }


}
