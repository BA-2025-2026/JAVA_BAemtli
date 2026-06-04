import TeamsAPI from "@/lib/api/Teams";
import TeamFeed from "@/components/TeamFeed/TeamFeed";

export default async function TeamWrapper() {
  // Fetch Teams
  let teams = [];

  try {
    teams = await TeamsAPI.readAll();
  } catch (error) {
    console.error("Teams konnten nicht geladen werden.", error);
  }

  return (
    <section>
      <TeamFeed teams={teams} />
    </section>
  );
}
