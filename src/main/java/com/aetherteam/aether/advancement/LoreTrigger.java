package com.aetherteam.aether.advancement;

import com.aetherteam.aether.Aether;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

/**
 * Criterion trigger used for checking an item placed by a player inside a Book of Lore.
 */
public class LoreTrigger extends SimpleCriterionTrigger<LoreTrigger.Instance> {
    private static final ResourceLocation ID = new ResourceLocation(Aether.MODID, "lore_entry");
    public static final LoreTrigger INSTANCE = new LoreTrigger();

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    protected Instance createInstance(JsonObject json, ContextAwarePredicate predicate, DeserializationContext context) {
        ItemPredicate itemPredicate = ItemPredicate.fromJson(json.get("item"));
        return new LoreTrigger.Instance(predicate, itemPredicate);
    }

    public void trigger(ServerPlayer player, ItemStack stack) {
        this.trigger(player, (instance) -> instance.test(stack));
    }

    public static class Instance extends AbstractCriterionTriggerInstance {
        private final ItemPredicate item;

        public Instance(ContextAwarePredicate predicate, ItemPredicate item) {
            super(LoreTrigger.ID, predicate);
            this.item = item;
        }

        public static LoreTrigger.Instance forItem(ItemPredicate item) {
            return new LoreTrigger.Instance(ContextAwarePredicate.ANY, item);
        }

        public static LoreTrigger.Instance forItem(ItemLike item) {
            return forItem(ItemPredicate.Builder.item().of(item).build());
        }

        public static LoreTrigger.Instance forAny() {
            return forItem(ItemPredicate.ANY);
        }

        public boolean test(ItemStack stack) {
            return this.item.matches(stack);
        }

        @Override
        public JsonObject serializeToJson(SerializationContext context) {
            JsonObject jsonObject = super.serializeToJson(context);
            jsonObject.add("item", this.item.serializeToJson());
            return jsonObject;
        }
    }
}
