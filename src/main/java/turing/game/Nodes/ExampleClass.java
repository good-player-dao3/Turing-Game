package turing.game.Nodes;

import turing.game.Nodes.Blocks.Blocks;
import turing.game.Nodes.Groups.Groups;
import turing.game.Nodes.Items.Items;
import turing.game.TGTuringGame;

public class ExampleClass {
    //@Override
    public static void ExampleModels() {
        TGTuringGame.LOGGER.info("ExampleModels()");
        TGTuringGame.LOGGER.info("ExampleModels: Group");
        Groups.init();
        TGTuringGame.LOGGER.info("ExampleModels: Item");
        Items.initialize();
        TGTuringGame.LOGGER.info("ExampleModels: Blocks");
        Blocks.initialize();
        TGTuringGame.LOGGER.info("ExampleModels() over");
    }
}