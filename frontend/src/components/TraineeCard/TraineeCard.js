import styles from "./TraineeCard.module.css";

export default function TraineeCard({ trainee }) {
  return (
    <div className={styles.traineeCard}>
      <div className={styles.traineeNameWrapper}>
        <p>{trainee.firstName}</p>
        <p>{trainee.lastName}</p>
      </div>
      <div className={styles.crudButtons}>
        <span className="material-symbols-outlined">edit</span>
        <span className="material-symbols-outlined">delete</span>
      </div>
    </div>
  );
}
