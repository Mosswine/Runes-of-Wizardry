package com.zpig333.runesofwizardry.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IChatComponent;

import com.zpig333.runesofwizardry.api.DustRegistry;
import com.zpig333.runesofwizardry.core.WizardryRegistry;

public class TileEntityDust extends TileEntity implements IInventory{

    public static final int size = 4;
    //Defines the current grid on the ground
    private int[][] pattern;

    public TileEntityDust(){
        //The grid is a 4x4 grid
        pattern = new int[size][size];
    }

    //Returns the coordinates of a dust piece in int[][] form
    public int getDust(int i, int j)
    {
        int rtn = pattern[i][j];
        return rtn;
    }

    //Sets a piece of dust
    public void setDust(EntityPlayer p, int i, int j, int dust)
    {
        if (p != null && !worldObj.canMineBlockBody(p, this.getPos()))
            return;
        int last = getDust(i, j);
        pattern[i][j] = dust;

        if (dust != 0 && last != dust)
        {
            int[] color = DustRegistry.getFloorColorRGB(dust);
            java.awt.Color c = new java.awt.Color(color[0], color[1], color[2]);
            c = c.darker();
            float r = c.getRed() / 255F;
            float g = c.getGreen() / 255F;
            float b = c.getBlue() / 255F;
            if (r == 0)
                r -= 1;

            if (Math.random() < 0.75){
                for (int d = 0; d < Math.random() * 3; d++)
                {
                    //(1.7.10)worldObj.spawnParticle("reddust", pos.getX() + i / 4D + Math.random() * 0.15, pos.getY(), pos.getZ() + j / 4D + Math.random() * 0.15, r, g, b);
                	//FIXME not sure what the last param is...
                    worldObj.spawnParticle(EnumParticleTypes.REDSTONE, pos.getX(), pos.getY(), pos.getZ(), 1/4D + Math.random() * 0.15, 0, j / 4D + Math.random() * 0.15,0);
                }

            }
        }
        worldObj.notifyBlockOfStateChange(pos, Blocks.air);
        worldObj.markBlockForUpdate(pos);
    }


    //Render arrays for the dust.
    public int[][][] getRendArrays()
    {
        int[][][] rtn = new int[3][size + 1][size + 1];
        int[][] n = new int[size + 2][size + 2]; // neighbors

        for (int x = 0; x < size; x++)
        {
            for (int z = 0; z < size; z++)
            {
                n[x + 1][z + 1] = getDust(x, z);
                rtn[0][x][z] = getDust(x, z);
            }
        }
        //worldObj.getBlock(xCoord - 1, yCoord, zCoord)
        if (DustRegistry.isDust(worldObj.getBlockState(pos.add(-1, 0, 0))))
        {
            TileEntityDust ted = (TileEntityDust) worldObj.getTileEntity(pos.add(-1, 0, 0));

            for (int i = 0; i < size; i++)
            {
                n[0][i + 1] = ted.getDust(size - 1, i);
            }
        }

        if (DustRegistry.isDust(worldObj.getBlockState(pos.add(1,0,0))))
        {
            TileEntityDust ted = (TileEntityDust) worldObj.getTileEntity(pos.add(1,0,0));

            for (int i = 0; i < size; i++)
            {
                n[size + 1][i + 1] = ted.getDust(0, i);
            }
        }

        if (DustRegistry.isDust(worldObj.getBlockState(pos.add(0,0,-1))))
        {
            TileEntityDust ted = (TileEntityDust) worldObj.getTileEntity(pos.add(0,0,-1));

            for (int i = 0; i < size; i++)
            {
                n[i + 1][0] = ted.getDust(i, size - 1);
            }
        }

        if (DustRegistry.isDust(worldObj.getBlockState(pos.add(0,0,1))))
        {
            TileEntityDust ted = (TileEntityDust) worldObj.getTileEntity(pos.add(0,0,1));

            for (int i = 0; i < size; i++)
            {
                n[i + 1][size + 1] = ted.getDust(i, 0);
            }
        }

        // horiz
        for (int x = 0; x < size; x++)
        {
            for (int y = 0; y < size + 1; y++)
            {
                if (n[x + 1][y] == n[x + 1][y + 1])
                {
                    rtn[1][x][y] = n[x + 1][y];
                }
            }
        }

        // vert
        for (int x = 0; x < size + 1; x++)
        {
            for (int y = 0; y < size; y++)
            {
                if (n[x][y + 1] == n[x + 1][y + 1])
                {
                    rtn[2][x][y] = n[x][y + 1];
                }
            }
        }
        return rtn;
    }


    /* Inventory stuffs */

    @Override
    public int getSizeInventory() {
        return size * size;
    }

    @Override
    public ItemStack getStackInSlot(int lock) {
        int y = lock % size;
        int x = (lock - size) / size;
        return new ItemStack(WizardryRegistry.dust_item, 1, pattern[x][y]);
    }

    @Override
    public ItemStack decrStackSize(int lock, int amount) {
        int y = lock % size;
        int x = (lock - size) / size;
        pattern[x][y] = 0;
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int lock, ItemStack stack) {

        int x = (lock - size) / size;
        int y = lock % size;
        int size_stack = stack.stackSize;
        int meta = stack.getItemDamage();
        Item item = stack.getItem();
        if(item == WizardryRegistry.dust_item && size_stack > 0){
            pattern[x][y] = meta;
        }

    }
    
    @Override
    public String getName(){
        return "rw.tile_dust";
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
        return false;
    }

    @Override
    public void openInventory(EntityPlayer player) {

    }

    @Override
    public void closeInventory(EntityPlayer player) {

    }

    @Override
    public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
        return false;
    }



	@Override
	public IChatComponent getDisplayName() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int getField(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getFieldCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}
}
