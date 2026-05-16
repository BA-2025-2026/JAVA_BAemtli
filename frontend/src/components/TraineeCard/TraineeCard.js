import styles from "./TraineeCard.module.css";
import DeleteModal from "../DeleteModal/DeleteModal";
import { useState } from "react";
import { deleteTraineeAction } from "@/actions/traineeActions";
import TraineeForm from "../TraineeForm/TraineeForm";

export default function TraineeCard({ trainee }) {
  // Delete Trainee Confirmation Modal
  const [isModalOpen, setIsModalOpen] = useState(false);
  // Edit Trainee State
  const [isEditingTrainee, setIsEditingTrainee] = useState(false);

  // Delete trainee handler
  const handleDeleteClick = () => setIsModalOpen(true);

  // Delete team confirmation modal handler
  const handleConfirmDelete = async () => {
    await deleteTraineeAction(trainee.id);
    setIsModalOpen(false);
  };

  return (
    <>
      <DeleteModal
        isOpen={isModalOpen}
        entityName={`${trainee.firstName} ${trainee.lastName}`}
        onCancel={() => setIsModalOpen(false)}
        onConfirm={handleConfirmDelete}
      />
      {!isEditingTrainee ? (
        <div className={styles.traineeCard}>
          <div className={styles.traineeNameWrapper}>
            <p>{trainee.firstName}</p>
            <p>{trainee.lastName}</p>
          </div>
          <div className={styles.crudButtons}>
            <span
              className="material-symbols-outlined"
              onClick={() => setIsEditingTrainee(true)}
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
        </div>
      ) : (
        <TraineeForm
          initialData={{
            id: trainee.id,
            teamId: trainee.teamId,
            firstName: trainee.firstName,
            lastName: trainee.lastName,
          }}
          onSuccess={() => setIsEditingTrainee(false)}
          onCancel={() => setIsEditingTrainee(false)}
        />
      )}
    </>
  );
}
