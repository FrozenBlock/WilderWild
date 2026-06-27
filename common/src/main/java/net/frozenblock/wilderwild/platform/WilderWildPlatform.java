package net.frozenblock.wilderwild.platform;

import java.util.ServiceLoader;

public final class WilderWildPlatform {

	private static <T> T load(Class<T> clazz) {
		return ServiceLoader.load(clazz, WilderWildPlatform.class.getClassLoader())
			.findFirst()
			.orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
	}
}
