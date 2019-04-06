package org.squiddev.plethora.integration.forestry;

import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IMutation;
import forestry.core.config.Constants;
import org.squiddev.plethora.api.meta.BasicMetaProvider;
import org.squiddev.plethora.integration.MetaWrapper;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

@MetaWrapper.MetaProvider.Inject(value = IMutation.class, modId = Constants.MOD_ID)
public class MetaMutation extends BasicMetaProvider<IMutation> {
	@Nonnull
	@Override
	public Map<String, ?> getMeta(@Nonnull IMutation mutation) {
		Map<String, Object> out = new HashMap<>(5);

		out.put("species1", mutation.getAllele0().getUID());
		out.put("species2", mutation.getAllele1().getUID());
		out.put("chance", mutation.getBaseChance());

		IChromosomeType[] karyotype = mutation.getRoot().getKaryotype();
		Map<String, Object> results = new HashMap<>(karyotype.length);

		for (IChromosomeType chromosome : karyotype) {
			for (IAllele allele : mutation.getTemplate()) {
				if (chromosome.getAlleleClass().isInstance(allele)) {
					results.put(chromosome.getName(), MetaGenome.getAlleleMeta(allele));
				}
			}
		}

		out.put("result", results);

		Map<Integer, String> conditions = new HashMap<>(mutation.getSpecialConditions().size());
		int i = 0;

		for (String condition : mutation.getSpecialConditions()) {
			conditions.put(++i, condition);
		}

		if (!conditions.isEmpty()) out.put("conditions", conditions);

		return out;
	}
}
