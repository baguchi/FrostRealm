package baguchan.frostrealm.client;

import baguchan.frostrealm.FrostRealm;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.unsafe.UnsafeHacks;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class FrostShaders {
	/*
	 *
	 * I referred from Tama's Void Scape
	 *
	 */
	public static BindableShaderInstance ENERGY_LIKE_ENTITY;

	public static void init(IEventBus bus) {
		bus.addListener((Consumer<RegisterShadersEvent>) event -> {
			try {
				event.registerShader(new BindableShaderInstance(event.getResourceManager(), new ResourceLocation(FrostRealm.MODID, "energy_like_entity"), DefaultVertexFormat.
						NEW_ENTITY), shader -> ENERGY_LIKE_ENTITY = (BindableShaderInstance) shader);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	public static class BindableShaderInstance extends ShaderInstance {

		private ShaderInstance last;

		public BindableShaderInstance(ResourceProvider p_173336_, ResourceLocation shaderLocation, VertexFormat p_173338_) throws IOException {
			super(p_173336_, shaderLocation, p_173338_);
		}

		ShaderInstance getSelf() {
			return this;
		}

		public final void bind(@Nullable Runnable exec) {
			last = RenderSystem.getShader();
			RenderSystem.setShader(this::getSelf);
			if (exec != null)
				exec.run();
			apply();
		}

		public final void runThenClear(Runnable exec) {
			exec.run();
			clear();
			RenderSystem.setShader(() -> last);
			last = null;
		}

		public final void invokeThenClear(@Nullable Runnable execBind, Runnable execPost) {
			bind(execBind);
			runThenClear(execPost);
		}

		public final void invokeThenClear(Runnable execPost) {
			invokeThenClear(null, execPost);
		}

		public final void invokeThenEndTesselator(@Nullable Runnable execBind) {
			invokeThenClear(execBind, () -> Tesselator.getInstance().end());
		}

		public final void invokeThenEndTesselator() {
			invokeThenClear(() -> Tesselator.getInstance().end());
		}

	}

	public static class WrappedBindableShaderInstance extends BindableShaderInstance {

		private Supplier<ShaderInstance> wrapped;

		/*
			DO NOT USE
		 */
		@SuppressWarnings("ConstantConditions")
		private WrappedBindableShaderInstance() throws IOException {
			super(null, null, null);
		}

		private static WrappedBindableShaderInstance make(Supplier<ShaderInstance> instance) {
			WrappedBindableShaderInstance wrapper = UnsafeHacks.newInstance(WrappedBindableShaderInstance.class);
			wrapper.wrapped = instance;
			return wrapper;
		}

		@Override
		ShaderInstance getSelf() {
			return wrapped.get();
		}

		@Override
		public void apply() {
			getSelf().apply();
		}

		@Override
		public void clear() {
			getSelf().clear();
		}
	}
}