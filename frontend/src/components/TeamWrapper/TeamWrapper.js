import TeamsAPI from "@/lib/api/Teams";
import TeamFeed from "@/components/TeamFeed/TeamFeed";
import styles from "./TeamWrapper.module.css";

export default async function ActivityWrapper() {
  // Fetch Teams
  const teams = await TeamsAPI.readAll();
  console.log(teams);

  return (
    <section>
      <h1>Teams & Lernende</h1>
      <TeamFeed />
    </section>
  );
}
