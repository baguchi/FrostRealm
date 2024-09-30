package baguchan.frostrealm.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class SnowParticle extends TextureSheetParticle {
    private static final float ACCELERATION_SCALE = 0.0025F;
    private static final int INITIAL_LIFETIME = 300;
    private static final int CURVE_ENDPOINT_TIME = 300;
    private static final float FALL_ACC = 0.25F;
    private static final float WIND_BIG = 2.0F;
    private float rotSpeed;
    private final float particleRandom;
    private final float spinAcceleration;

    public SnowParticle(ClientLevel p_277612_, double p_278010_, double p_277614_, double p_277673_, double xd, double yd, double zd) {
        super(p_277612_, p_278010_, p_277614_, p_277673_);
        this.rotSpeed = (float) Math.toRadians(this.random.nextBoolean() ? -30.0 : 30.0);
        this.particleRandom = this.random.nextFloat();
        this.spinAcceleration = (float) Math.toRadians(this.random.nextBoolean() ? -5.0 : 5.0);
        this.lifetime = 200;
        this.gravity = 0.005F;
        float f = 0.15F;
        this.quadSize = f;
        this.setSize(f, f);
        this.friction = 1.0F;
        this.xd = xd;
        this.yd = yd;
        this.zd = zd;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.lifetime-- <= 0) {
            this.remove();
        }

        if (!this.removed) {
            float f = (float) (300 - this.lifetime);
            float f1 = Math.min(f / 300.0F, 1.0F);
            double d0 = Math.cos(Math.toRadians((double) (this.particleRandom * 60.0F))) * 2.0 * Math.pow((double) f1, 1.25);
            double d1 = Math.sin(Math.toRadians((double) (this.particleRandom * 60.0F))) * 2.0 * Math.pow((double) f1, 1.25);
            this.xd += d0 * 0.005F;
            this.zd += d1 * 0.005F;
            this.yd = this.yd - (double) this.gravity;
            this.rotSpeed = this.rotSpeed + this.spinAcceleration / 20.0F;
            this.oRoll = this.roll;
            this.roll = this.roll + this.rotSpeed / 20.0F;
            this.move(this.xd, this.yd, this.zd);
            if (this.onGround || this.lifetime < 299 && (this.xd == 0.0 || this.zd == 0.0)) {
                this.remove();
            }

            if (!this.removed) {
                this.xd = this.xd * (double) this.friction;
                this.yd = this.yd * (double) this.friction;
                this.zd = this.zd * (double) this.friction;
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public Provider(SpriteSet p_105793_) {
            this.sprite = p_105793_;
        }

        public Particle createParticle(
                SimpleParticleType p_105804_,
                ClientLevel p_105805_,
                double p_105806_,
                double p_105807_,
                double p_105808_,
                double p_105809_,
                double p_105810_,
                double p_105811_
        ) {
            SnowParticle bubbleparticle = new SnowParticle(p_105805_, p_105806_, p_105807_, p_105808_, p_105809_, p_105810_, p_105811_);
            bubbleparticle.pickSprite(this.sprite);
            return bubbleparticle;
        }
    }
}
