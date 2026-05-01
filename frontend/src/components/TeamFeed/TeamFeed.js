import TeamSection from "../TeamSection/TeamSection";

export default function TeamFeed({ teams }) {
  return (
    <>
      <div>
        {teams.map((team) => (
          <TeamSection key={team.id} team={team} />
        ))}
      </div>
      {teams.length === 0 && <p>Keine Teams gefunden.</p>}
    </>
  );
}
