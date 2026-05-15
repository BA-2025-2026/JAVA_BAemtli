"use client";

import { useState } from "react";
import { deleteTeamAction } from "@/actions/teamActions";
import TraineeCard from "../TraineeCard/TraineeCard";
import DeleteModal from "../DeleteModal/DeleteModal";
import TeamForm from "../TeamForm/TeamForm";
import styles from "./TeamSection.module.css";

export default function TeamSection({ team }) {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isEditingTeam, setIsEditingTeam] = useState(false);

  // Delete team handler
  const handleDeleteClick = () => setIsModalOpen(true);

  // Delete team confirmation modal handler
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
        {!isEditingTeam ? (
          <>
            <h4>{team.name}</h4>
            <div className={styles.crudButtons}>
              <span
                className="material-symbols-outlined"
                onClick={() => setIsEditingTeam(true)}
              >
                edit
              </span>
              <span
                className="material-symbols-outlined"
                onClick={handleDeleteClick}
              >
                delete
              </span>
            </div>
          </>
        ) : (
          <TeamForm
            initialData={{ id: team.id, name: team.name }}
            onSuccess={() => setIsEditingTeam(false)}
            onCancel={() => setIsEditingTeam(false)}
          />
        )}
      </div>
      <div className={styles.traineeList}>
        {team.trainees.map((trainee) => (
          <TraineeCard key={trainee.id} trainee={trainee} />
        ))}
        {team.trainees.length === 0 && (
          <div className="noEntityInfo">
            <p>Noch keine Lernenden vorhanden.</p>
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
