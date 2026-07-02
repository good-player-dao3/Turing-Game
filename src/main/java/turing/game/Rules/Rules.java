package turing.game.Rules;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.level.GameRules;

public class Rules {
    public static final GameRules.Key<GameRules.BooleanValue> POWERED_RAIL_CAN_COMPARATOR =
            GameRuleRegistry.register(
                    "powered_rail_can_comparator",
                    GameRules.Category.MOBS,
                    GameRuleFactory.createBooleanRule(false)
            );

    public static void Initialize()
    {

    }
}
