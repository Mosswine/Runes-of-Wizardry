package com.zpig333.runesofwizardry.block.itemblocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import com.zpig333.runesofwizardry.core.References;


public class ItemBlockDustBlocks extends ItemBlock {

    public ItemBlockDustBlocks(Block block) {
        super(block);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int meta){
        return meta;
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack){
        int meta = itemStack.getItemDamage();

        //Universal metadata checker- checks to see if metadata is within conceivable bounds.
        if(meta < 0 || meta >= References.dust_types.length){
            meta = 0;
        }
        return super.getUnlocalizedName() + "." + References.dust_types[meta];
    }


}
