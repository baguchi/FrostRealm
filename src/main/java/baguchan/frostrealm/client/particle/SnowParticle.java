package baguchan.frostrealm.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;

public class SnowParticle extends SingleQuadParticle {
    private static final float ACCELERATION_SCALE = 0.0025F;
    private static final int INITIAL_LIFETIME = 300;
    private static final int CURVE_ENDPOINT_TIME = 300;
    private static final float FALL_ACC = 0.25F;
    private static final float WIND_BIG = 2.0F;
    private float rotSpeed;
    private final float particleRandom;
    private final float spinAcceleration;

    public SnowParticle(ClientLevel p_277612_, double p_278010_, double p_277614_, double p_277673_, double xd, double yd, double zd, TextureAtlasSprite sprite) {
        super(p_277612_, p_278010_, p_277614_, p_277673_, sprite);
        this.rotSpeed = (float) Math.toRadians(this.random.nextBoolean() ? -30.0 : 30.0);
        this.particleRandom = this.random.nextFloat();
        this.spinAcceleration = (float) Math.toRadians(this.random.nextBoolean() ? -5.0 : 5.0);
        this.lifetime = 200;
        this.gravity = 0.015F;
        float f = 0.15F;
        this.quadSize = f;
        this.setSize(f, f);
        this.friction = 1.0F;
        this.xd *= 0.1F;
        this.yd *= 0.1F;
        this.zd *= 0.1F;
        this.xd += xd;
        this.yd += yd;
        this.zd += zd;
    }

    @Override
    public SingleQuadParticle.Layer getLayer() {
        return SingleQuadParticle.Layer.OPAQUE;
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
            this.rotSpeed = this.rotSpeed + this.spinAcceleration / 20.0F;
            this.oRoll = this.roll;
            this.roll = this.roll + this.rotSpeed / 20.0F;
            this.move(this.xd, this.yd, this.zd);
            if (this.onGround || this.lifetime < 200 && (this.xd == 0.0 || this.zd == 0.0)) {
                this.remove();
            }

            if (!this.removed) {
                this.xd = this.xd * (double) this.friction;
                this.yd = this.yd * (double) this.friction;
                this.zd = this.zd * (double) this.friction;
            }
        }
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public Provider(SpriteSet p_105793_) {
            this.sprite = p_105793_;
        }

        @Override
        public Particle createParticle(SimpleParticleType p_446632_, ClientLevel p_107095_, double x, double y, double z, double motionX, double motionY, double motionZ, RandomSource random) {
            return new SnowParticle(p_107095_, x, y, z, motionX, motionY, motionZ, this.sprite.get(random));
        }
    }
}
