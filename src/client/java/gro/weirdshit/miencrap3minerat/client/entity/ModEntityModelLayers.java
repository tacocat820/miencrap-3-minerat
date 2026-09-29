package gro.weirdshit.miencrap3minerat.client.entity;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.client.entity.classes.ActualFishRenderer;
import gro.weirdshit.miencrap3minerat.client.entity.classes.AnglerfishRenderer;
import gro.weirdshit.miencrap3minerat.client.entity.classes.BaaRenderer;
import gro.weirdshit.miencrap3minerat.client.entity.classes.CableRenderer;
import gro.weirdshit.miencrap3minerat.client.entity.classes.CrishRenderer;
import gro.weirdshit.miencrap3minerat.client.entity.classes.DamnRenderer;
import gro.weirdshit.miencrap3minerat.client.entity.classes.PtlRenderer;
import gro.weirdshit.miencrap3minerat.client.entity.classes.DolphinRenderer;
import gro.weirdshit.miencrap3minerat.client.entity.classes.EnderDragonRenderer;
import gro.weirdshit.miencrap3minerat.client.entity.classes.FishRenderer;
import gro.weirdshit.miencrap3minerat.client.entity.classes.FloatRenderer;
import gro.weirdshit.miencrap3minerat.client.entity.classes.FundyRenderer;
import gro.weirdshit.miencrap3minerat.client.entity.classes.GamingChairRenderer;
import gro.weirdshit.miencrap3minerat.client.entity.classes.GigachickenRenderer;
import gro.weirdshit.miencrap3minerat.client.entity.classes.HangingVillagerRenderer;
import gro.weirdshit.miencrap3minerat.client.entity.classes.HappyGhastRenderer;
import gro.weirdshit.miencrap3minerat.client.entity.classes.HmmmRenderer;
import gro.weirdshit.miencrap3minerat.client.entity.classes.HooorseRenderer;
import gro.weirdshit.miencrap3minerat.client.entity.classes.MerlRenderer;
import gro.weirdshit.miencrap3minerat.client.entity.classes.NotjuanRenderer;
import gro.weirdshit.miencrap3minerat.client.entity.classes.NotjuanSpitRenderer;
import gro.weirdshit.miencrap3minerat.client.entity.classes.PcEntityRenderer;
import gro.weirdshit.miencrap3minerat.client.entity.classes.PolPotRenderer;
import gro.weirdshit.miencrap3minerat.client.entity.classes.PsychoRenderer;
import gro.weirdshit.miencrap3minerat.client.entity.classes.PuffercopterRenderer;
import gro.weirdshit.miencrap3minerat.client.entity.classes.RedLightningBoltRenderer;
import gro.weirdshit.miencrap3minerat.client.entity.classes.RepeerRenderer;
import gro.weirdshit.miencrap3minerat.client.entity.classes.SlaveRenderer;
import gro.weirdshit.miencrap3minerat.client.entity.classes.SteveJobsRenderer;
import gro.weirdshit.miencrap3minerat.client.entity.classes.TetoRenderer;
import gro.weirdshit.miencrap3minerat.client.entity.classes.WaterDeerRenderer;
import gro.weirdshit.miencrap3minerat.entity.WaterDeer;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.Identifier;

public class ModEntityModelLayers {
	public static final ModelLayerLocation NOTJUAN = createMain("notjuan");
	public static final ModelLayerLocation DOLPHIN = createMain("dolphin");
	public static final ModelLayerLocation FUNDY = createMain("fundy");
	public static final ModelLayerLocation MERL = createMain("merl");
	public static final ModelLayerLocation FISH = createMain("fish");
	public static final ModelLayerLocation GAMING_CHAIR = createMain("gaming_chair");
	public static final ModelLayerLocation BAA = createMain("baa");
	public static final ModelLayerLocation CABLE = createMain("cable");
	public static final ModelLayerLocation GIGACHICKEN = createMain("gigachicken");
	public static final ModelLayerLocation PTL = createMain("ptl");
	public static final ModelLayerLocation CRISH = createMain("crish");
	public static final ModelLayerLocation ACTUAL_FISH = createMain("actual_fish");
	public static final ModelLayerLocation HAPPY_GHAST = createMain("happy_ghast");
	public static final ModelLayerLocation HOOORSE = createMain("hooorse");
	public static final ModelLayerLocation HANGING_VILLAGER = createMain("hanging_villager");
	public static final ModelLayerLocation STEVE_JOBS = createMain("steve_jobs");
	public static final ModelLayerLocation HMMM = createMain("hmmm");
	public static final ModelLayerLocation WATER_DEER = createMain("water_deer");
	public static final ModelLayerLocation SLAVE = createMain("slave");
	public static final ModelLayerLocation ENDER_DRAGON = createMain("ender_dragon");
	public static final ModelLayerLocation POL_POT = createMain("pol_pot");
	public static final ModelLayerLocation TETO = createMain("teto");
	public static final ModelLayerLocation ANGLERFISH = createMain("anglerfish");
	public static final ModelLayerLocation DAMN = createMain("damn");
	public static final ModelLayerLocation PSYCHO = createMain("psycho");
	public static final ModelLayerLocation FLOAT = createMain("float");
	public static final ModelLayerLocation REPEER = createMain("repeer");
	public static final ModelLayerLocation PUFFERCOPTER = createMain("puffercopter");
	public static final ModelLayerLocation NOTJUAN_SPIT = createMain("notjuan_spit");
	public static final ModelLayerLocation RED_LIGHTNING_BOLT = createMain("red_lightning_bolt");
	public static final ModelLayerLocation PC = createMain("pc");

	private static ModelLayerLocation createMain(String name) {
		return new ModelLayerLocation(Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, name), "main");
	}

	public static void registerModelLayers() {
		NotjuanRenderer.register(NOTJUAN);
		DolphinRenderer.register(DOLPHIN);
		FundyRenderer.register(FUNDY);
		MerlRenderer.register(MERL);
		FishRenderer.register(FISH);
		GamingChairRenderer.register(GAMING_CHAIR);
		BaaRenderer.register(BAA);
		CableRenderer.register(CABLE);
		GigachickenRenderer.register(GIGACHICKEN);
		PtlRenderer.register(PTL);
		CrishRenderer.register(CRISH);
		ActualFishRenderer.register(ACTUAL_FISH);
		HappyGhastRenderer.register(HAPPY_GHAST);
		HooorseRenderer.register(HOOORSE);
		HangingVillagerRenderer.register(HANGING_VILLAGER);
		SteveJobsRenderer.register(STEVE_JOBS);
		HmmmRenderer.register(HMMM);
		WaterDeerRenderer.register(WATER_DEER);
		EnderDragonRenderer.register(ENDER_DRAGON);
		SlaveRenderer.register(SLAVE);
		PolPotRenderer.register(POL_POT);
		TetoRenderer.register(TETO);
		AnglerfishRenderer.register(ANGLERFISH);
		DamnRenderer.register(DAMN);
		PsychoRenderer.register(PSYCHO);
		FloatRenderer.register(FLOAT);
		RepeerRenderer.register(REPEER);
		PuffercopterRenderer.register(PUFFERCOPTER);
		NotjuanSpitRenderer.register(NOTJUAN_SPIT);
		RedLightningBoltRenderer.register(RED_LIGHTNING_BOLT);
		PcEntityRenderer.register(PC);
	}
}
