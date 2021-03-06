package com.zpig333.runesofwizardry.client.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

import com.zpig333.runesofwizardry.api.DustRegistry;
import com.zpig333.runesofwizardry.block.BlockDust;
import com.zpig333.runesofwizardry.tileentity.TileEntityDust;

public class RenderBlockDust implements ISimpleBlockRenderingHandler {

    public static int dust_modelid;
    public int current_renderer;

    public RenderBlockDust(int modelID){
        this.current_renderer = modelID;
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {

    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        if(current_renderer == dust_modelid){
            renderDust(renderer, world, x, y, z, block);
            return true;
        }
        else{
            return false;
        }

    }


    public boolean renderDust(RenderBlocks renderblocks, IBlockAccess iblock, int i, int j, int k, Block block)
    {
        int meta = iblock.getBlockMetadata(i, j, k);
        boolean drawHightlight = (meta == 1 || meta == 3);
        int size = TileEntityDust.size;
        float px = 1F / 16F;
        float cellWidth = 1F / size;
        float h = 0.025F;
        TileEntityDust ted = (TileEntityDust) iblock.getTileEntity(i, j, k);
        float t = 0.02F;
        Tessellator tes = Tessellator.instance;
        tes.setBrightness(block.getMixedBrightnessForBlock(iblock, i, j, k));

        int[][][] rendArray = ted.getRendArrays();
        int[][] midArray = rendArray[0]; // Actual points
        int[][] horizArray = rendArray[1]; // horizontal connectors
        int[][] vertArray = rendArray[2]; // vertical connectors
        float bx, bz, bw, bl;
        int[] col;
        float r, g, b;

        float highlightHeight = 0.125f;

        for (int x = 0; x < size + 1; x++) {
            for (int z = 0; z < size + 1; z++) {
                float ox = x * cellWidth;
                float oz = z * (cellWidth);

                if (midArray[x][z] != 0) {

                    col = DustRegistry.getFloorColorRGB(midArray[x][z]);
                    r = col[0];
                    g = col[1];
                    b = col[2];

                    if (meta == BlockDust.DUST_ACTIVE || meta == BlockDust.DUST_ACTIVATING) {
                        r = 255f;
                        g = 0f;
                        b = 0f;
                    } else if (meta == BlockDust.DUST_DEAD) {
                        r = 178f;
                        g = 178f;
                        b = 178f;
                    }

                    r = r / 255;
                    g = g / 255;
                    b = b / 255;

                    bx = ox + px;
                    bz = oz + px;
                    bw = 2 * px;
                    bl = 2 * px;
                    block.setBlockBounds(bx, t, bz, bx + bw, t + h, bz + bl);

                    renderblocks.setRenderBoundsFromBlock(block);
                    renderblocks.renderStandardBlockWithColorMultiplier(block,
                            i, j, k, r, g, b);

                    if (drawHightlight) {
                        if (meta == BlockDust.DUST_ACTIVATING) {
                            tes.setColorOpaque_F(1, 1, 1);
                            block.setBlockBounds(bx, t, bz, bx + bw,
                                    highlightHeight, bz + bl);
                        } else {
                            tes.setColorOpaque_F(1, 0.68f, 0.68f);
                            block.setBlockBounds(bx, t, bz, bx + bw, t + h, bz
                                    + bl);
                        }
                        tes.setBrightness(15728880);
                    }
                }

                if (horizArray[x][z] != 0) {

                    col = DustRegistry.getFloorColorRGB(horizArray[x][z]);
                    r = col[0];
                    g = col[1];
                    b = col[2];

                    if (meta == BlockDust.DUST_ACTIVE || meta == BlockDust.DUST_ACTIVATING) {
                        r = 255f;
                        g = 0f;
                        b = 0f;
                    } else if (meta == BlockDust.DUST_DEAD) {
                        r = 178f;
                        g = 178f;
                        b = 178f;
                    }

                    r = r / 255;
                    g = g / 255;
                    b = b / 255;

                    bx = ox + px;
                    bz = oz - px;
                    bw = 2 * px;
                    bl = 2 * px;

                    if (z == 0) {
                        bz = 0;
                        bl = px;
                    }

                    if (z == size) {
                        bl = px;
                    }

                    block.setBlockBounds(bx, t, bz, bx + bw, t + h, bz + bl);

                    renderblocks.setRenderBoundsFromBlock(block);
                    renderblocks.renderStandardBlockWithColorMultiplier(block,
                            i, j, k, r, g, b);

                    if (drawHightlight) {
                        if (meta == BlockDust.DUST_ACTIVATING) {
                            tes.setColorOpaque_F(1, 1, 1);
                            block.setBlockBounds(bx, t, bz, bx + bw,
                                    highlightHeight, bz + bl);
                        } else {
                            tes.setColorOpaque_F(1, 0.68f, 0.68f);
                            block.setBlockBounds(bx, t, bz, bx + bw, t + h, bz
                                    + bl);
                        }
                        tes.setBrightness(15728880);
                    }
                }

                if (vertArray[x][z] != 0) {

                    col = DustRegistry.getFloorColorRGB(vertArray[x][z]);
                    r = col[0];
                    g = col[1];
                    b = col[2];

                    if (meta == BlockDust.DUST_ACTIVE || meta == BlockDust.DUST_ACTIVATING) {
                        r = 255f;
                        g = 0f;
                        b = 0f;
                    } else if (meta == BlockDust.DUST_DEAD) {
                        r = 178f;
                        g = 178f;
                        b = 178f;
                    }

                    r = r / 255;
                    g = g / 255;
                    b = b / 255;

                    bx = ox - px;
                    bz = oz + px;
                    bw = 2 * px;
                    bl = 2 * px;

                    if (x == 0) {
                        bx = 0;
                        bw = px;
                    }

                    if (x == size) {
                        bw = px;
                    }

                    block.setBlockBounds(bx, t, bz, bx + bw, t + h, bz + bl);

                    renderblocks.setRenderBoundsFromBlock(block);
                    renderblocks.renderStandardBlockWithColorMultiplier(block,
                            i, j, k, r, g, b);

                    if (drawHightlight) {
                        if (meta == 3) {
                            tes.setColorOpaque_F(1, 1, 1);
                            block.setBlockBounds(bx, t, bz, bx + bw,
                                    highlightHeight, bz + bl);
                        } else {
                            tes.setColorOpaque_F(1, 0.68f, 0.68f);
                            block.setBlockBounds(bx, t, bz, bx + bw, t + h, bz
                                    + bl);
                        }
                        tes.setBrightness(15728880);
                    }
                }
            }
        }
        block.setBlockBounds(0, 0, 0, 0, 0, 0);
        renderblocks.setRenderBoundsFromBlock(block);
        renderblocks.renderStandardBlockWithColorMultiplier(block, i, j, k, 1, 1, 1);
        renderblocks.overrideBlockTexture = null;
        block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, h, 1.0F);
        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return false;
    }

    @Override
    public int getRenderId() {
        return current_renderer;
    }
}
