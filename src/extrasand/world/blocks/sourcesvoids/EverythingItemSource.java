package extrasand.world.blocks.sourcesvoids;

import arc.*;
import arc.graphics.g2d.*;
import arc.util.*;
import extrasand.graphics.*;
import mindustry.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.meta.*;

public class EverythingItemSource extends Block{
    public float ticksPerItemColor = 90f;

    public TextureRegion strobeRegion, centerRegion;

    public EverythingItemSource(String name){
        super(name);
        requirements(Category.distribution, BuildVisibility.sandboxOnly, ItemStack.empty);
        alwaysUnlocked = true;

        hasItems = true;
        update = true;
        solid = true;
        group = BlockGroup.transportation;
        noUpdateDisabled = true;
        envEnabled = Env.any;
    }

    @Override
    public void load(){
        super.load();
        strobeRegion = Core.atlas.find(name + "-strobe", "extra-sand-redux-source-strobe");
        centerRegion = Core.atlas.find(name + "-center", "center");
    }

    @Override
    public void setBars(){
        super.setBars();

        removeBar("items");
    }

    public class EverythingItemSourceBuild extends Building{
        @Override
        public void draw(){
            super.draw();

            EXSDrawf.setStrobeColor();
            Draw.rect(strobeRegion, x, y);

            Draw.color(Tmp.c1.lerp(PMPal.itemColors, Time.time / (ticksPerItemColor * PMPal.itemColors.length) % 1f));
            Draw.rect(centerRegion, x, y);
            Draw.color();
        }

        @Override
        public void updateTile(){
            Vars.content.items().each(i -> {
                items.set(i, 1);
                dump(i);
                items.set(i, 0);
            });
        }

        @Override
        public boolean acceptItem(Building source, Item item){
            return false;
        }
    }
}
