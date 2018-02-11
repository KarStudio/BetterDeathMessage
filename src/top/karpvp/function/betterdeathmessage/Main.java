package top.karpvp.function.betterdeathmessage;

import java.text.NumberFormat;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{

    @Override
    public void onEnable() {
        getLogger().info("成功载入插件BetterDeathMessage");
        Bukkit.getPluginManager().registerEvents(this, this);
//        CommandListener cmd = new CommandListener();
//        getCommand("KarUHCAdmin").setExecutor(cmd);
//        getCommand("KarUHC").setExecutor(cmd);
//        saveDefaultConfig();
//        reloadConfig();
    }
    
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        Player p = event.getEntity();
        EntityDamageEvent ede = p.getLastDamageCause();
        EntityDamageEvent.DamageCause dc = ede.getCause();
        if (dc==EntityDamageEvent.DamageCause.PROJECTILE){
            if (p.getKiller()!=null){
                Player killer = p.getKiller();
                Location klc = killer.getLocation();Location lc = p.getLocation();
                double x1 =lc.getX();double y1 =lc.getY();double z1 =lc.getZ();
                double x2 = klc.getX();double y2 = klc.getY();double z2 = klc.getZ();
                double flc = (x2-x1)*(x2-x1)+(y2-y1)*(y2-y1)+(z2-z1)*(z2-z1);
                flc = Math.sqrt(flc);
                NumberFormat nf = NumberFormat.getInstance();
                nf.setMaximumFractionDigits(2);
                double health = killer.getHealth();
                getServer().broadcastMessage("§c§l§m"+p.getDisplayName()+"§7 被 §a§l"+killer.getDisplayName()+"§7(§c§l"+nf.format(health)+"§c❤§7) 射死了(§f§l"+nf.format(flc)+"§7米远)");
            }
        }else{
            if (p.getKiller()!=null){
                NumberFormat nf = NumberFormat.getInstance();
                nf.setMaximumFractionDigits(2);
                double health = p.getKiller().getHealth();
                getServer().broadcastMessage("§c§l§m"+p.getDisplayName()+"§7 被 §a§l"+p.getKiller().getDisplayName()+"§7(§c§l"+nf.format(health)+"§c❤§7)杀死了");
            }
        }
    }
    
//    @EventHandler
//    public void onPlayerDamage(EntityDamageEvent event){
//        if (event.getEntity() instanceof Player){
//            Player p = (Player)event.getEntity();
//            EntityDamageEvent ede = p.getLastDamageCause();
//            EntityDamageEvent.DamageCause dc =null;
//            try{
//            dc = ede.getCause();
//            }catch(Exception e){}
//            if (dc==EntityDamageEvent.DamageCause.PROJECTILE){
//                if (p.getKiller()!=null){
//                    Player killer = p.getKiller();
//                    Location klc = killer.getLocation();Location lc = p.getLocation();
//                    double x1 =lc.getX();double y1 =lc.getY();double z1 =lc.getZ();
//                    double x2 = klc.getX();double y2 = klc.getY();double z2 = klc.getZ();
//                    double flc = (x2-x1)*(x2-x1)+(y2-y1)*(y2-y1)+(z2-z1)*(z2-z1);
//                    flc = Math.sqrt(flc);
//                    if (flc > 50){
//                        NumberFormat nf = NumberFormat.getInstance();
//                        nf.setMaximumFractionDigits(2);
//                        double health = p.getHealth();
//                        double khealth = killer.getHealth();
//                        getServer().broadcastMessage("§c§l"+p.getDisplayName()+"§7(§c§l"+nf.format(health)+"❤§7) 被 §a§l"+killer.getDisplayName()+"§7(§c§l"+nf.format(khealth)+"§c❤§7) 从§f§l"+nf.format(flc)+"§7米连续外射中了两次");
//                    }
//                }
//            }
//        }
//    }
    
    @EventHandler(priority=EventPriority.HIGH)
    public void DamageEvent(EntityDamageByEntityEvent entity){
        if ((entity.getDamager() instanceof Arrow)){
            Arrow arrow = (Arrow)entity.getDamager();
            if ((arrow.getShooter() instanceof Player)){
                Player player = (Player)arrow.getShooter();
                Damageable pl = (Damageable)entity.getEntity();
                if ((pl instanceof Player))
                {
                    Player v = (Player)pl;
                    double ptviev = pl.getHealth();
                    Integer damage = Integer.valueOf((int)entity.getFinalDamage());
                    if (!pl.isDead())
                    {
                    Integer realHealth = Integer.valueOf((int)(ptviev - damage.intValue()));
                    NumberFormat nf = NumberFormat.getInstance();
                    nf.setMaximumFractionDigits(2);
                    if (realHealth.intValue() > 0)
                        player.sendMessage("§6§lK§e§lar §7§l>§7你对 §a§l" + v.getName() + "§7造成了§f§l"+nf.format(entity.getFinalDamage())+"§7,点伤害,剩余血量§f" + realHealth + "/20§c❤");
                    }
                    Location klc = player.getLocation();Location lc = v.getLocation();
                    double x1 =lc.getX();double y1 =lc.getY();double z1 =lc.getZ();
                    double x2 = klc.getX();double y2 = klc.getY();double z2 = klc.getZ();
                    double flc = (x2-x1)*(x2-x1)+(y2-y1)*(y2-y1)+(z2-z1)*(z2-z1);
                    flc = Math.sqrt(flc);
                    if (flc > 50){
                        NumberFormat nf = NumberFormat.getInstance();
                        nf.setMaximumFractionDigits(2);
                        double health = v.getHealth();
                        double khealth = player.getHealth();
                        getServer().broadcastMessage("§c§l"+v.getDisplayName()+"§7(§c§l"+nf.format(health)+"❤§7) 被 §a§l"+player.getDisplayName()+"§7(§c§l"+nf.format(khealth)+"§c❤§7) 从§f§l"+nf.format(flc)+"§7米外射中了");
                    }
                }
            }
        }
    }
}
