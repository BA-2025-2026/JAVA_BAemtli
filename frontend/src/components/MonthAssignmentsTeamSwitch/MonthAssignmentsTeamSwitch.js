import styles from "./MonthAssignmentsTeamSwitch.module.css";

function getTeamInitials(teamName) {
  return teamName.trim().slice(0, 2).toUpperCase();
}

export default function MonthAssignmentsTeamSwitch({ teams, monthName }) {
  return (
    <div className={styles.switch} aria-label={`Team selection for ${monthName}`}>
      {teams.map((team) => (
        <button
          key={team.id}
          type="button"
          className={styles.teamButton}
          disabled
          aria-label={team.name}
        >
          {getTeamInitials(team.name)}
        </button>
      ))}
    </div>
  );
}
