"use server";

import TraineesAPI from "@/lib/api/Trainee";
import { revalidatePath } from "next/cache";
import { z } from "zod";

const traineeValidationScheama = z.object({
  // Parse to int, in case id gets passed in as string
  teamId: z.coerce.number().int().min(1, "Team ID wurde nicht erkannt."),
  firstName: z
    .string()
    .max(30, "Vorname darf maximal 30 Zeichen lang sein.")
    .min(1, "Vorname darf nicht leer sein."),
  lastName: z
    .string()
    .max(30, "Nachname darf maximal 30 Zeichen lang sein.")
    .min(1, "Nachname darf nicht leer sein."),
});

export async function createUpdateTrainee(prevState, formData) {
  // Verify Session
  const session = await verifySession();

  if (!session) {
    redirect("/login");
  }

  // Parse and Validate
  const data = Object.fromEntries(formData);
  const validated = traineeValidationScheama.safeParse(data);

  // Case: Validation error
  if (!validated.success) {
    return {
      id: prevState?.id,
      fields: data, // keep current entries of user
      errors: validated.error.flatten().fieldErrors,
      success: false,
    };
  }

  try {
    const { teamId } = validated.data;
    const { firstName } = validated.data;
    const { lastName } = validated.data;

    if (prevState?.id) {
      // UPDATE (PATCH)
      await TraineesAPI.update(prevState.id, { firstName, lastName });
    } else {
      // CREATE
      await TraineesAPI.create({ teamId, firstName, lastName });
    }

    // Update cache so UI will show changes
    revalidatePath("/teams");

    // Return success
    return { success: true, errors: {}, fields: {} };
  } catch (error) {
    let errorMessage = "An unexpected error occurred.";

    // Evaluate 4xx client errors
    if (error.response >= 400 && error.response < 500) {
      errorMessage = "Invalid input data. Please check entry fields.";
    }
    // Evaluate 5xx server errors
    else if (error.response >= 500) {
      errorMessage = "Server error. Please try again later.";
    }

    return {
      success: false,
      message: errorMessage,
    };
  }
}

export async function deleteTraineeAction(id) {
  // Verify session

  // Delete Team (hand in JWT Token later)
  await TraineesAPI.delete(id);
  revalidatePath("/teams");
}
