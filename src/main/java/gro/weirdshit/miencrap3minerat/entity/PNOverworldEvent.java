package gro.weirdshit.miencrap3minerat.entity;

import net.minecraft.world.entity.Marker;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;

public class PNOverworldEvent extends Marker {
	public PNOverworldEvent(final EntityType<?> type, final Level level) {
		super(type, level);
		this.noPhysics = true;
		born = 0;
	}

	long born;

	public void tick() {
		super.tick();

		if (level() instanceof ServerLevel) {

			if (born == 0) born = level().getGameTime();
			MIENCRAP3MINERAT.LOGGER.info("adsd");
			
			float gtoff = (float)(level().getGameTime() % 5000L) / 50.0f * Mth.PI;
			float gtoff2 = (float)(level().getGameTime() % 5000L) / 25.0f * Mth.PI;
			float gtoff3 = (float)(level().getGameTime() % 6000L) / 20.0f * Mth.PI;
			double x = 70.0 * Mth.sin(gtoff) * Mth.cos(gtoff2) + 50.0 * Mth.sin(gtoff3);
			double z = 60.0 * Mth.cos(gtoff2) * Mth.sin(gtoff3) + 60.0 * Mth.cos(gtoff);

			//x = this.getX(); z = this.getZ();
			MIENCRAP3MINERAT.LOGGER.info(String.valueOf(x));
			MIENCRAP3MINERAT.LOGGER.info(String.valueOf(z));

			PrimedTnt tnt = new PrimedTnt(level(), this.getX() + x, this.getY() + 50.0, this.getZ() + z, null);
			tnt.setFuse(100);
			level().addFreshEntity(tnt);

			if (level().getGameTime() - born > 800) this.discard();
		}

	}
	@Override
	protected void addAdditionalSaveData(final ValueOutput output) {
		super.addAdditionalSaveData(output);
		output.putLong("born", this.born);
	}

	@Override
	protected void readAdditionalSaveData(final ValueInput input) {
		super.readAdditionalSaveData(input);
		this.born = input.getLongOr("born", 0);
	}

}
