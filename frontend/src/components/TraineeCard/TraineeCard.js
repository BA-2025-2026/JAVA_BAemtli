import styles from "./TraineeCard.module.css";
import { useState } from "react";

export default function TraineeCard({ trainee }) {
  const [editingId, setEditingId] = useState(null);

  console.log(trainee);

  return (
    <div className={styles.traineeCard}>
      <div>
        <p>{trainee.firstName}</p>
        <p>{trainee.lastName}</p>
      </div>
      <div className={styles.crudButtons}>
        <span
          className="material-symbols-outlined"
          onClick={() => setEditingId(team.id)}
        >
          edit
        </span>
        <span
          className="material-symbols-outlined"
          onClick={() => handleDelete(team.id)}
        >
          delete
        </span>
      </div>
    </div>
  );
}
