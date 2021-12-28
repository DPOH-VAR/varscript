package ru.dpohvar.varscript.extension;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;

public class LivingEntityExt {
    public static Block getTarb(LivingEntity self) {
        return self.getTargetBlock(null, 128);
    }

    public static Location getEye(LivingEntity self) {
        return self.getEyeLocation();
    }

    public static int getAir(LivingEntity self) {
        return self.getRemainingAir();
    }

    public static void setAir(LivingEntity self, int air) {
        self.setRemainingAir(air);
    }

    public static boolean isLookAt(LivingEntity self, Entity entity) {
        return self.hasLineOfSight(entity);
    }

    public static boolean isPickup(LivingEntity self) {
        return self.getCanPickupItems();
    }

    public static void setPickup(LivingEntity self, boolean v) {
        self.setCanPickupItems(v);
    }

    public static EntityEquipment getEq(LivingEntity self) {
        return self.getEquipment();
    }

    public static Entity getLeash(LivingEntity self) {
        return self.getLeashHolder();
    }

    public static void setLeash(LivingEntity self, Entity holder) {
        self.setLeashHolder(holder);
    }

    public static ItemStack getBoots(LivingEntity self) {
        return self.getEquipment().getBoots();
    }

    public static ItemStack getHelmet(LivingEntity self) {
        return self.getEquipment().getHelmet();
    }

    public static ItemStack getLegs(LivingEntity self) {
        return self.getEquipment().getLeggings();
    }

    public static ItemStack getPants(LivingEntity self) {
        return self.getEquipment().getLeggings();
    }

    public static ItemStack getHand(LivingEntity self) {
        return self.getEquipment().getItemInMainHand();
    }

    public static ItemStack getOffHand(LivingEntity self) {
        return self.getEquipment().getItemInOffHand();
    }

    public static ItemStack getArmor(LivingEntity self) {
        return self.getEquipment().getChestplate();
    }

    public static void setBoots(LivingEntity self, ItemStack item) {
        self.getEquipment().setBoots(item);
    }

    public static void setHelmet(LivingEntity self, ItemStack item) {
        self.getEquipment().setHelmet(item);
    }

    public static void setLegs(LivingEntity self, ItemStack item) {
        self.getEquipment().setLeggings(item);
    }

    public static void setPants(LivingEntity self, ItemStack item) {
        self.getEquipment().setLeggings(item);
    }

    public static void setHand(LivingEntity self, ItemStack item) {
        self.getEquipment().setItemInMainHand(item);
    }

    public static void setOffHand(LivingEntity self, ItemStack item) {
        self.getEquipment().setItemInOffHand(item);
    }

    public static void setArmor(LivingEntity self, ItemStack item) {
        self.getEquipment().setChestplate(item);
    }

    public static void setBoots(LivingEntity self, Material item) {
        setBoots(self, new ItemStack(item));
    }

    public static void setHelmet(LivingEntity self, Material item) {
        setHelmet(self, new ItemStack(item));
    }

    public static void setLegs(LivingEntity self, Material item) {
        setLegs(self, new ItemStack(item));
    }

    public static void setPants(LivingEntity self, Material item) {
        setLegs(self, new ItemStack(item));
    }

    public static void setHand(LivingEntity self, Material item) {
        setHand(self, new ItemStack(item));
    }

    public static void setArmor(LivingEntity self, Material item) {
        setArmor(self, new ItemStack(item));
    }

    public static void setBoots(LivingEntity self, String item) {
        setBoots(self, new ItemStack(Material.matchMaterial(item)));
    }

    public static void setHelmet(LivingEntity self, String item) {
        setHelmet(self, new ItemStack(Material.matchMaterial(item)));
    }

    public static void setLegs(LivingEntity self, String item) {
        setLegs(self, new ItemStack(Material.matchMaterial(item)));
    }

    public static void setPants(LivingEntity self, String item) {
        setLegs(self, new ItemStack(Material.matchMaterial(item)));
    }

    public static void setHand(LivingEntity self, String item) {
        setHand(self, new ItemStack(Material.matchMaterial(item)));
    }

    public static void setArmor(LivingEntity self, String item) {
        setArmor(self, new ItemStack(Material.matchMaterial(item)));
    }
}
