"use server";

import TeamsAPI from "@/lib/api/Teams";
import { revalidatePath } from "next/cache";
import { success, z } from "zod";

const teamSchema = {
  teamName: "",
};

const teamValidationScheama = z.object({
  teamName: z
    .string()
    .min(2, "Teamname muss mindestens zwei Zeichen lang sein."),
});

export async function createUpdateTeam(prevState, formData) {
  // Verify Session

  // Parse and Validate
  const data = Object.fromEntries(formData);
  const validated = teamValidationScheama.safeParse(data);

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
    const { teamName } = validated.data;
    if (prevState?.id) {
      // UPDATE (PATCH)
      await TeamsAPI.update(prevState.id, { name: teamName });
    } else {
      // CREATE
      await TeamsAPI.create({ name: teamName });
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

export async function deleteTeamAction(id) {
  // verify session first?
  // Delete Team (hand in JWT Token later)
  await TeamsAPI.delete(id);
  revalidatePath("/teams");
}
