package spichka.arrowline;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.Items;
import net.minecraft.util.math.Vec3d;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;

public class ArrowLineClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;
            PlayerEntity player = client.player;
            if (player.getActiveItem().getItem() instanceof BowItem && player.isUsingItem()) {
                Vec3d start = player.getEyePos();
                Vec3d look = player.getRotationVecClient();
                float pull = (float)player.getItemUseTime() / 20.0f;
                pull = Math.min(pull, 1.0f);
                double velocity = pull * 3.0;
                Vec3d motion = look.multiply(velocity);
                for (int i = 0; i < 40; i++) {
                    double t = i * 1.5;
                    Vec3d pos = start.add(motion.multiply(t)).add(0, -0.05 * t * t, 0); // гравитация
                    ParticleEffect effect = ParticleTypes.FLAME;
                    client.world.addParticleClient(effect, pos.x, pos.y, pos.z, 0.0, 0.5, 0.0);
                }
            }
        });
    }
}