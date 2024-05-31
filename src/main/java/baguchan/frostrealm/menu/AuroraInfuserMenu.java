package baguchan.frostrealm.menu;

import baguchan.frostrealm.block.AuroraInfuserBlock;
import baguchan.frostrealm.capability.FrostWeatherManager;
import baguchan.frostrealm.capability.FrostWeatherSavedData;
import baguchan.frostrealm.message.ChangeAuroraMessage;
import baguchan.frostrealm.registry.*;
import baguchan.frostrealm.utils.aurorapower.AuroraPowerInstance;
import baguchan.frostrealm.utils.aurorapower.AuroraPowerUtils;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;

public class AuroraInfuserMenu extends AbstractContainerMenu {
    private final Container enchantSlots = new SimpleContainer(2) {
        public void setChanged() {
            super.setChanged();
            AuroraInfuserMenu.this.slotsChanged(this);
        }
    };
    private final ContainerLevelAccess access;
    private final RandomSource random = RandomSource.create();
    private final DataSlot enchantmentSeed = DataSlot.standalone();
    public final int[] costs = new int[3];
    public final int[] auroraClue = new int[]{-1, -1, -1};
    public final int[] levelClue = new int[]{-1, -1, -1};

    public AuroraInfuserMenu(int p_39454_, Inventory p_39455_) {
        this(p_39454_, p_39455_, ContainerLevelAccess.NULL);
    }

    public AuroraInfuserMenu(int p_39457_, Inventory p_39458_, ContainerLevelAccess p_39459_) {
        super(FrostMenuTypes.AURORA_INFUSER.get(), p_39457_);
        this.access = p_39459_;
        this.addSlot(new Slot(this.enchantSlots, 0, 15, 47) {
            public boolean mayPlace(ItemStack p_39508_) {
                return true;
            }

            public int getMaxStackSize() {
                return 1;
            }
        });
        this.addSlot(new Slot(this.enchantSlots, 1, 35, 47) {
            public boolean mayPlace(ItemStack p_39517_) {
                return p_39517_.is(FrostTags.Items.AURORA_FUELS);
            }

            public int getMaxStackSize() {
                return 10;
            }
        });

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(p_39458_, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(p_39458_, k, 8 + k * 18, 142));
        }

        this.addDataSlot(DataSlot.shared(this.costs, 0));
        this.addDataSlot(DataSlot.shared(this.costs, 1));
        this.addDataSlot(DataSlot.shared(this.costs, 2));
        this.addDataSlot(this.enchantmentSeed).set(p_39458_.player.getEnchantmentSeed());
        this.addDataSlot(DataSlot.shared(this.auroraClue, 0));
        this.addDataSlot(DataSlot.shared(this.auroraClue, 1));
        this.addDataSlot(DataSlot.shared(this.auroraClue, 2));
        this.addDataSlot(DataSlot.shared(this.levelClue, 0));
        this.addDataSlot(DataSlot.shared(this.levelClue, 1));
        this.addDataSlot(DataSlot.shared(this.levelClue, 2));
    }

    public void slotsChanged(Container p_39461_) {
        if (p_39461_ == this.enchantSlots) {
            ItemStack itemstack = p_39461_.getItem(0);
            ItemStack itemstack2 = p_39461_.getItem(1);
            if (!itemstack.isEmpty() && AuroraPowerUtils.getAuroraPowers(itemstack).isEmpty() && !itemstack2.isEmpty()) {
                this.access.execute((p_39485_, p_39486_) -> {
                    int j = 0;
                    this.random.setSeed((long) this.enchantmentSeed.get());

                    int counts = itemstack2.getCount() + 1;
                    for (int k = 0; k < 3; ++k) {
                        this.costs[k] = Mth.clamp(random.nextInt(counts) + (counts / 2), 1, 30);
                        this.auroraClue[k] = -1;
                        this.levelClue[k] = -1;
                        if (this.costs[k] < k + 1) {
                            this.costs[k] = 0;
                        }
                    }

                    for (int l = 0; l < 3; ++l) {
                        if (this.costs[l] > 0) {
                            List<AuroraPowerInstance> list = this.getAuroraPowerList(itemstack, l, this.costs[l]);
                            if (list != null && !list.isEmpty()) {
                                AuroraPowerInstance enchantmentinstance = list.get(this.random.nextInt(list.size()));
                                this.auroraClue[l] = AuroraPowers.getRegistry().getId(enchantmentinstance.auroraPower);
                                this.levelClue[l] = enchantmentinstance.level;
                            }
                        }
                    }
                    for (BlockPos blockpos : AuroraInfuserBlock.OFFSETS) {
                        p_39485_.removeBlock(blockpos, false);
                    }
                    this.broadcastChanges();
                });
            } else {
                for (int i = 0; i < 3; ++i) {
                    this.costs[i] = 0;
                    this.auroraClue[i] = -1;
                    this.levelClue[i] = -1;
                }
            }
        }

    }

    public boolean clickMenuButton(Player p_39465_, int p_39466_) {
        if (p_39466_ >= 0 && p_39466_ < this.costs.length) {
            ItemStack itemstack = this.enchantSlots.getItem(0);
            ItemStack itemstack1 = this.enchantSlots.getItem(1);
            int i = p_39466_ + 1;
            if ((itemstack1.isEmpty() || itemstack1.getCount() < i) && !p_39465_.getAbilities().instabuild) {
                return false;
            } else if (this.costs[p_39466_] <= 0 || itemstack.isEmpty() || (FrostWeatherManager.getAuroraLevel() < this.costs[p_39466_] * 0.01F) && !p_39465_.getAbilities().instabuild || p_39465_.level().dimension() != FrostDimensions.FROSTREALM_LEVEL) {
                return false;
            } else {
                this.access.execute((p_39481_, p_39482_) -> {
                    ItemStack itemstack2 = itemstack;
                    List<AuroraPowerInstance> list = this.getAuroraPowerList(itemstack, p_39466_, this.costs[p_39466_]);

                    p_39465_.onEnchantmentPerformed(itemstack2, 0);
                    if (!list.isEmpty()) {
                        for (int j = 0; j < list.size(); ++j) {
                            AuroraPowerInstance enchantmentinstance = list.get(j);
                            AuroraPowerUtils.auroraInfusion(itemstack2, enchantmentinstance.auroraPower, enchantmentinstance.level);
                        }

                        if (!p_39465_.getAbilities().instabuild) {
                            FrostWeatherSavedData.get(p_39481_).setAuroraLevel(FrostWeatherSavedData.get(p_39481_).getAuroraLevel() - this.costs[p_39466_] * 0.01F);
                            FrostWeatherSavedData.get(p_39481_).setUnstableLevel(FrostWeatherSavedData.get(p_39481_).getUnstableLevel() + this.costs[p_39466_] * 0.01F);
                            ChangeAuroraMessage message2 = new ChangeAuroraMessage(FrostWeatherSavedData.get(p_39481_).getAuroraLevel());
                            if (p_39481_ instanceof ServerLevel serverLevel) {
                                PacketDistributor.sendToPlayersInDimension(serverLevel, message2);
                            }
                            this.enchantSlots.setItem(1, ItemStack.EMPTY);
                        }

                        this.enchantSlots.setChanged();
                        this.enchantmentSeed.set(p_39465_.getEnchantmentSeed());
                        this.slotsChanged(this.enchantSlots);
                        p_39481_.playSound((Player) null, p_39482_, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 1.0F, p_39481_.random.nextFloat() * 0.1F + 0.9F);
                    }
                });
                return true;
            }
        } else {
            Util.logAndPauseIfInIde(p_39465_.getName() + " pressed invalid button id: " + p_39466_);
            return false;
        }
    }

    private List<AuroraPowerInstance> getAuroraPowerList(ItemStack p_39472_, int p_39473_, int p_39474_) {
        this.random.setSeed((long) (this.enchantmentSeed.get() + p_39473_));
        List<AuroraPowerInstance> list = AuroraPowerUtils.selectAuroraPower(this.random, p_39472_, p_39474_, false);
        return list;
    }

    public int getGoldCount() {
        ItemStack itemstack = this.enchantSlots.getItem(1);
        return itemstack.isEmpty() ? 0 : itemstack.getCount();
    }

    public int getEnchantmentSeed() {
        return this.enchantmentSeed.get();
    }

    public void removed(Player p_39488_) {
        super.removed(p_39488_);
        this.access.execute((p_39469_, p_39470_) -> {
            this.clearContainer(p_39488_, this.enchantSlots);
        });
    }

    public boolean stillValid(Player p_39463_) {
        return stillValid(this.access, p_39463_, FrostBlocks.AURORA_INFUSER.get());
    }

    public ItemStack quickMoveStack(Player p_39490_, int p_39491_) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(p_39491_);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (p_39491_ == 0) {
                if (!this.moveItemStackTo(itemstack1, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (p_39491_ == 1) {
                if (!this.moveItemStackTo(itemstack1, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (itemstack1.is(FrostTags.Items.AURORA_FUELS)) {
                if (!this.moveItemStackTo(itemstack1, 1, 2, true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (this.slots.get(0).hasItem() || !this.slots.get(0).mayPlace(itemstack1)) {
                    return ItemStack.EMPTY;
                }

                ItemStack itemstack2 = itemstack1.copy();
                itemstack2.setCount(1);
                itemstack1.shrink(1);
                this.slots.get(0).set(itemstack2);
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(p_39490_, itemstack1);
        }

        return itemstack;
    }
}
