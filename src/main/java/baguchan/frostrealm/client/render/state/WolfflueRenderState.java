package baguchan.frostrealm.client.render.state;

import net.minecraft.client.renderer.entity.state.HoldingEntityRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class WolfflueRenderState extends HoldingEntityRenderState {
    private static final ResourceLocation DEFAULT_TEXTURE = ResourceLocation.withDefaultNamespace("textures/entity/wolf/wolf.png");
    public boolean isAngry;
    public boolean isSitting;
    public boolean saddle;
    public ResourceLocation texture = DEFAULT_TEXTURE;
    @Nullable
    public DyeColor collarColor;
    public ItemStack bodyArmorItem = ItemStack.EMPTY;
    public float headRollAngle;
    public final AnimationState idleSitAnimationState = new AnimationState();
    public final AnimationState idleSit2AnimationState = new AnimationState();
    public final AnimationState jumpAnimationState = new AnimationState();
    public float running;
    public float tailAngle;

}
