import TeamsAPI from "@/lib/api/Teams";
import TeamFeed from "@/components/TeamFeed/TeamFeed";

export default async function TeamWrapper() {
  // Fetch Teams
  const teams = await TeamsAPI.readAll();

  return (
    <section>
      <TeamFeed teams={teams} />
    </section>
  );
}
