package net.frozenblock.wilderwild.misc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.util.Objects;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.client.Minecraft;

public class CompetitionCounter {
	private static final boolean FIREFLY_CAPTURE_COMPETITION = true;
	private static final boolean ANCIENT_HORN_KILL_COMPETITION = true;

	public static void addFireflyCapture(boolean creative, boolean natural) {
		if (FIREFLY_CAPTURE_COMPETITION) {
			Minecraft client = Minecraft.getInstance();
			if (client != null && client.player != null) {
				File directory = new File(client.gameDirectory, "wilderwild");
				File directory1 = new File(directory, "competitions");
				File destination = new File(directory1, "fireflies.wild");
				directory1.mkdirs();
				if (creative && destination.exists()) {
					WilderWild.logInsane("WHAT!?!??! YOU'RE PLAYING IN CREATIVE MODE!!!!!! YOUR FIREFLY CAPTURING DATA IS BEING DELETED, I CAN NOT TOLERATE CHEATING ON THE FIREFLY COUNTER!!!!!!", true);
					destination.delete();
					return;
				}
				if (!natural) {
					WilderWild.logInsane("Oh no! The firefly you captured was not spawned via natural means! These will not count towards the firefly counter, you must locate a biome that spawns fireflies and capture natural ones there.", true);
					return;
				}
				Gson gson = new GsonBuilder()
						.disableHtmlEscaping()
						.setPrettyPrinting()
						.serializeNulls()
						.create();

				int count = 0;
				FireflyCounter flyCounterObj = new FireflyCounter();
				flyCounterObj.setPlayerUUID(client.player.getStringUUID());

				if (destination.exists()) {
					try (Reader reader = Files.newBufferedReader(destination.toPath())) {
						JsonElement counter = JsonParser.parseReader(reader);
						if (counter.isJsonObject()) {
							JsonObject obj = counter.getAsJsonObject();
							count = obj.get("firefly_count").getAsInt();
							String UUID = obj.get("player_uuid").getAsString();
							if (!Objects.equals(UUID, flyCounterObj.getPlayerUUID())) {
								WilderWild.logInsane("Using alt accounts to capture fireflies is against the rules as it can create inaccurate firefly capture information. Please delete wilderwild/competitions/fireflies.wild or switch back to the first account you captured a firefly with to continue.", true);
								return;
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				flyCounterObj.setCount(count + 1);

				String json = gson.toJson(flyCounterObj);

				try {
					FileWriter writer = new FileWriter(destination);
					writer.write(json);
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}


	}

	public static void addAncientHornKill(boolean creative, boolean natural) {
		if (ANCIENT_HORN_KILL_COMPETITION) {
			Minecraft client = Minecraft.getInstance();
			if (client != null && client.player != null) {
				File directory = new File(client.gameDirectory, "wilderwild");
				File directory1 = new File(directory, "competitions");
				File destination = new File(directory1, "ancient_horn_kills.wild");
				directory1.mkdirs();
				if (creative && destination.exists()) {
					WilderWild.logInsane("WHAT!?!??! YOU'RE PLAYING IN CREATIVE MODE!!!!!! YOUR ANCIENT HORN KILL DATA IS BEING DELETED, I WILL NEVER TOLERATE YOUR INSOLENCE!!!!!!", true);
					destination.delete();
					return;
				}
				if (!natural) {
					WilderWild.logInsane("Oh no! How terrible! THE ANCIENT HORN KILL IS NOT NATURAL!!!! BE NATURAL NOW!!!!!", true);
					return;
				}
				Gson gson = new GsonBuilder()
						.disableHtmlEscaping()
						.setPrettyPrinting()
						.serializeNulls()
						.create();

				int count = 0;
				AncientHornKillCounter ancientKillCounter = new AncientHornKillCounter();
				ancientKillCounter.setPlayerUUID(client.player.getStringUUID());

				if (destination.exists()) {
					try (Reader reader = Files.newBufferedReader(destination.toPath())) {
						JsonElement counter = JsonParser.parseReader(reader);
						if (counter.isJsonObject()) {
							JsonObject obj = counter.getAsJsonObject();
							count = obj.get("kill_count").getAsInt();
							String UUID = obj.get("player_uuid").getAsString();
							if (!Objects.equals(UUID, ancientKillCounter.getPlayerUUID())) {
								WilderWild.logInsane("Using alt accounts to kill mobs with an Ancient Horn is against the rules as it can create inaccurate Ancient Horn killing information. Please delete wilderwild/competitions/ancient_horn_kills.wild or switch back to the first account you killed a mob using the Ancient Horn with to continue.", true);
								return;
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				ancientKillCounter.setCount(count + 1);

				String json = gson.toJson(ancientKillCounter);

				try {
					FileWriter writer = new FileWriter(destination);
					writer.write(json);
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}


	}

	public static class FireflyCounter {

		private int firefly_count;
		private String player_uuid;

		public int getCount() {
			return firefly_count;
		}

		public void setCount(int count) {
			this.firefly_count = count;
		}

		public String getPlayerUUID() {
			return player_uuid;
		}

		public void setPlayerUUID(String uuid) {
			this.player_uuid = uuid;
		}

	}

	public static class AncientHornKillCounter {

		private int kill_count;
		private String player_uuid;

		public int getCount() {
			return kill_count;
		}

		public void setCount(int count) {
			this.kill_count = count;
		}

		public String getPlayerUUID() {
			return player_uuid;
		}

		public void setPlayerUUID(String uuid) {
			this.player_uuid = uuid;
		}

	}
}
