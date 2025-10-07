package baguchan.frostrealm.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;

public class VenomBubbleParticle extends SingleQuadParticle {
    protected VenomBubbleParticle(ClientLevel p_105773_, double p_105774_, double p_105775_, double p_105776_, double p_105777_, double p_105778_, double p_105779_, TextureAtlasSprite sprite) {
        super(p_105773_, p_105774_, p_105775_, p_105776_, sprite);
        this.setSize(0.02F, 0.02F);
        this.quadSize = this.quadSize * (this.random.nextFloat() * 0.6F + 0.2F);
        this.xd = p_105777_ * 0.2F + (Math.random() * 2.0 - 1.0) * 0.02F;
        this.yd = p_105778_ * 0.2F + (Math.random() * 2.0 - 1.0) * 0.02F;
        this.zd = p_105779_ * 0.2F + (Math.random() * 2.0 - 1.0) * 0.02F;
        this.lifetime = (int) (8.0 / (Math.random() * 0.8 + 0.2));
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.lifetime-- <= 0) {
            this.remove();
        } else {
            this.yd += 0.002;
            this.move(this.xd, this.yd, this.zd);
            this.xd *= 0.85F;
            this.yd *= 0.85F;
            this.zd *= 0.85F;
            if (!this.level.getFluidState(BlockPos.containing(this.x, this.y, this.z)).isEmpty()) {
                this.remove();
            }
        }
    }

    @Override
    public SingleQuadParticle.Layer getLayer() {
        return SingleQuadParticle.Layer.OPAQUE;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public Provider(SpriteSet p_105793_) {
            this.sprite = p_105793_;
        }

        @Override
        public Particle createParticle(SimpleParticleType p_446632_, ClientLevel p_107095_, double x, double y, double z, double motionX, double motionY, double motionZ, RandomSource random) {
            return new VenomBubbleParticle(p_107095_, x, y, z, motionX, motionY, motionZ, this.sprite.get(random));
        }
    }
}
