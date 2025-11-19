package dev.cammiescorner.icarus.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.cammiescorner.icarus.api.client.IcarusAPIClient;
import dev.cammiescorner.icarus.client.IcarusClient;
import dev.cammiescorner.icarus.client.IcarusModels;
import dev.cammiescorner.icarus.client.models.*;
import dev.cammiescorner.icarus.init.IcarusItems;
import dev.cammiescorner.icarus.item.WingItem;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

//TODO clean up
public class WingsLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
    private final FeatheredWingsModel<T> featheredWings;
    private final LeatherWingsModel<T> leatherWings;
    private final LightWingsModel<T> lightWings;
    private final FlandresWingsModel<T> flandresWings;
    private final DiscordsWingsModel<T> discordsWings;
    private final ZanzasWingsModel<T> zanzasWings;

    private static final Map<Item, ResourceLocation[]> TEXTURE_LOOKUP = new Reference2ObjectOpenHashMap<>();

    public WingsLayer(RenderLayerParent<T, M> context, EntityModelSet loader) {
        super(context);
        this.featheredWings = new FeatheredWingsModel<>(loader.bakeLayer(IcarusModels.FEATHERED));
        this.leatherWings = new LeatherWingsModel<>(loader.bakeLayer(IcarusModels.LEATHER));
        this.lightWings = new LightWingsModel<>(loader.bakeLayer(IcarusModels.LIGHT));
        this.flandresWings = new FlandresWingsModel<>(loader.bakeLayer(IcarusModels.FLANDRE));
        this.discordsWings = new DiscordsWingsModel<>(loader.bakeLayer(IcarusModels.DISCORD));
        this.zanzasWings = new ZanzasWingsModel<>(loader.bakeLayer(IcarusModels.ZANZA));
    }

    @Override
    public void render(PoseStack pose, MultiBufferSource bufferSource, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        var stack = IcarusAPIClient.getWingsForRendering(entity);

        if (stack.getItem() instanceof WingItem wingItem && IcarusClient.shouldRenderWings(entity)) {
            var wingModel = switch (wingItem.getWingType()) {
                case FEATHERED, MECHANICAL_FEATHERED -> featheredWings;
                case DRAGON, MECHANICAL_LEATHER -> leatherWings;
                case LIGHT -> lightWings;
                case UNIQUE -> {
                    if (stack.is(IcarusItems.FLANDRES_WINGS.get())) {
                        yield flandresWings;
                    }
                    if (stack.is(IcarusItems.DISCORDS_WINGS.get())) {
                        yield discordsWings;
                    }
                    if (stack.is(IcarusItems.ZANZAS_WINGS.get())) {
                        yield zanzasWings;
                    }
                    yield null;
                }
                default -> null;
            };
            if (wingModel == null) {
                return;
            }

            var textures = TEXTURE_LOOKUP.computeIfAbsent(wingItem, item -> {
                var baseId = BuiltInRegistries.ITEM.getKey(item).withPrefix("textures/entity/icarus/wings/");
                return new ResourceLocation[] {
                    baseId.withSuffix(".png"),
                    baseId.withSuffix("_2.png")
                };
            });

            pose.pushPose();
            pose.translate(0.0D, 0.0D, 0.125D);
            this.getParentModel().copyPropertiesTo(wingModel);
            wingModel.setupAnim(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
            this.renderWings(wingModel, pose, bufferSource, stack, RenderType.entityTranslucent(textures[0]), light, 0xFFFFFFFF);
            this.renderWings(wingModel, pose, bufferSource, stack, RenderType.entityTranslucent(textures[1]), light, 0xFFFFFFFF);
            pose.popPose();
        }
    }

    public void renderWings(WingEntityModel<T> model, PoseStack matrices, MultiBufferSource vertexConsumers, @Nullable ItemStack stack, RenderType renderLayer, int light, int color) {
        VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(vertexConsumers, renderLayer, stack != null && stack.hasFoil());
        model.renderToBuffer(matrices, vertexConsumer, light, OverlayTexture.NO_OVERLAY, color);
    }
}
