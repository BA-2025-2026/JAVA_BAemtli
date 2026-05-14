"use client";

import { useState } from "react";
import TraineeCard from "../TraineeCard/TraineeCard";
import DeleteModal from "../DeleteModal/DeleteModal";
import { deleteTeamAction } from "@/actions/teamActions";
import styles from "./TeamSection.module.css";

export default function TeamSection({ team }) {
  const [isModalOpen, setIsModalOpen] = useState(false);

  const handleDeleteClick = () => {
    setIsModalOpen(true);
  };

  const handleConfirmDelete = async () => {
    await deleteTeamAction(team.id);
    setIsModalOpen(false);
  };

  return (
    <>
      <DeleteModal
        isOpen={isModalOpen}
        entityName={team.name}
        onCancel={() => setIsModalOpen(false)}
        onConfirm={handleConfirmDelete}
      />
      <div className={styles.teamTitleCard}>
        <h4>{team.name}</h4>
        <div className={styles.crudButtons}>
          <span className="material-symbols-outlined">edit</span>
          <span
            className="material-symbols-outlined"
            onClick={handleDeleteClick}
          >
            delete
          </span>
        </div>
      </div>
      <div className={styles.traineeList}>
        {team.trainees.map((trainee) => (
          <TraineeCard key={trainee.id} trainee={trainee} />
        ))}
        {team.trainees.length === 0 && (
          <div className={`${styles.teamTitleCard} ${styles.noEntityInfo}`}>
            <h4>Noch keine Lernenden vorhanden.</h4>
          </div>
        )}
        <div className={styles.addTraineeSection}>
          <div>
            <h4>Lernende*r</h4>
            <h4>hinzufügen</h4>
          </div>
          <span className="material-symbols-outlined">add</span>
        </div>
      </div>
    </>
  );
}
