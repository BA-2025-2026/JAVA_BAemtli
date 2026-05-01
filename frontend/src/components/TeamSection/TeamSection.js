import styles from "./TeamSection.module.css";

export default function TeamSection({ team }) {
  return (
    <div className={styles.teamTitleCard}>
      <h4>{team.name}</h4>
      <div className={styles.crudButtons}>
        <span className="material-symbols-outlined">edit</span>
        <span className="material-symbols-outlined">delete</span>
      </div>
    </div>
  );
}
