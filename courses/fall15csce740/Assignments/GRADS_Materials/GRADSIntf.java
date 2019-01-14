/**
 * GRADSIntf
 * Interface for the GRADS top-level API.
 * Last Modified: 09/22/2015
 * 
 * Copyright (c) 2015, Gregory Gay
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 * 
 * - Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * - Redistributions in binary form must reproduce the above copyright notice, 
 *   this list of conditions and the following disclaimer in the documentation 
 *   and/or other materials provided with the distribution.
 * - Neither the name of the University of Minnesota nor the names of its 
 *   contributors may be used to endorse or promote products derived from this 
 *   software without specific prior written permission.
 *   
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE 
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
 * POSSIBILITY OF SUCH DAMAGE.
 */

package edu.sc.csce740;

import java.util.List;
import edu.sc.csce740.model.StudentRecord;
import edu.sc.csce740.model.ProgressSummary;
import edu.sc.csce740.model.CourseTaken;

/**
 * This defines the interface between the driver of the 
 * Graduation Requirement Assessment Data System (GRADS) 
 * and the processing code for checking and generating the
 * graduation progress summary.
 * 
 * NOTE:  When you implement this interface, it is good practice to create
 * your own exceptions for the project.  We expect this to be done for this 
 * project.
 * 
 *
 * @author ggay
 */


/**
 * Your implementation of this interface must be named GRADS
 */
public interface GRADSIntf
{
    /**
     * Loads the list of system usernames and permissions.
     * @param usersFile the filename of the users file.
     * @throws Exception for I/O errors.  SEE NOTE IN CLASS HEADER.
     */
    public void loadUsers(String usersFile) throws Exception;

    /**
     * Loads the list of valid courses.
     * @param coursesFile the filename of the users file.
     * @throws Exception for I/O errors.  SEE NOTE IN CLASS HEADER.
     */
    public void loadCourses(String coursesFile) throws Exception;

    /**
     * Loads the list of system transcripts.
     * @param recordsFile the filename of the transcripts file.
     * @throws Exception for I/O errors.  SEE NOTE IN CLASS HEADER.
     */
    public void loadRecords(String recordsFile) throws Exception;

    /**
     * Sets the user id of the user currently using the system.
     * @param userId  the id of the user to log in.
     * @throws Exception  if the user id is invalid.  SEE NOTE IN CLASS HEADER.
     */
    public void setUser(String userId) throws Exception;

    /**
     * Closes the current session, logs the user out, and clears and session data.
     * @throws Exception  if the user id is invalid.  SEE NOTE IN CLASS HEADER.
     */
    public void clearSession() throws Exception;
    
    /**
     * Gets the user id of the user currently using the system.
     * @return  the user id of the user currently using the system.
     */
    public String getUser();
    
    /**
     * Gets a list of the userIds of the students that a GPC can view.
     * @return a list containing the userId of for each student in the
     *      system belonging to the current user 
     * @throws Exception is the current user is not a GPC.
     */
    public List<String> getStudentIDs() throws Exception;
    
    /**
     * Gets the raw student record data for a given userId.
     * @param userId  the identifier of the student.
     * @return  the student record data.
     * @throws Exception  if the form data could not be retrieved.  SEE NOTE IN 
     *      CLASS HEADER.
     */
    public StudentRecord getTranscript(String userId) 
            throws Exception;
    
    /**
     * Saves a new set of student data to the records data.  
     * @param userId the student ID to overwrite.
     * @param transcript  the new student record
     * @param permanent  a status flag indicating whether (if false) to make a 
     * temporary edit to the in-memory structure or (if true) a permanent edit.
     * @throws Exception  if the transcript data could not be saved or failed
     * a validity check.  SEE NOTE IN CLASS HEADER.
     */
    public void updateTranscript(String userId, StudentRecord transcript, Boolean permanent) 
            throws Exception;

    /**
     * Appends a note to a student record.  
     * @param userId the student ID to add a note to.
     * @param note  the note to append
     * @param permanent  a status flag indicating whether (if false) to make a 
     * temporary edit to the in-memory structure or (if true) a permanent edit.
     * @throws Exception  if the note could not be saved or a non-GPC tries to call. 
     * SEE NOTE IN CLASS HEADER.
     */
    public void addNote(String userId, String note, Boolean permanent) 
            throws Exception;

    /**
     * Generates progress summary
     * @param userId the student to generate the record for.
     * @returns the student's progress summary in a data class matching the I/O file.
     * @throws Exception  if the progress summary could not be generated.  
     * SEE NOTE IN CLASS HEADER.
     */
    public ProgressSummary generateProgressSummary(String userId) 
            throws Exception;

    /**
     * Generates a new progress summary, assuming that the student passes the
     * provided set of prospective courses.
     * @param userId the student to generate the record for.
     * @param courses a list of the prospective courses.
     * @returns the student's hypothetical progress summary
     * @throws Exception  if the progress summary could not be generated or the courses  
     * are invalid. SEE NOTE IN CLASS HEADER.
     */
    public ProgressSummary simulateCourses(String userId, List<CourseTaken> courses) 
            throws Exception;
}
