import styles from "./page.module.css";
import TeamWrapper from "@/components/TeamWrapper/TeamWrapper";

export default function Teams() {
  return (
    <div>
      <TeamWrapper />
      <div className={`${styles.teamTitleCard} ${styles.addTeamSection}`}>
        <span className="material-symbols-outlined">add</span>
        <h4>Neues Team anlegen</h4>
      </div>
    </div>
  );
}
