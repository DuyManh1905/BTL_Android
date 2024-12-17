package com.duymanh.btl.api;

import com.duymanh.btl.dto.ApplicationFormDTO;
import com.duymanh.btl.dto.JobSuggestionDTO;
import com.duymanh.btl.model.Cv;
import com.duymanh.btl.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("/api/job/all")
    Call<ApiResponseJobFitUser> getAllJobs();

    @GET("/api/job/searchAll-mobile")
    Call<ApiResponseJobFitUser> searchAll(
            @Query("title") String title,
            @Query("area") String area,
            @Query("major") String major,
            @Query("minExperience") String minExperience,
            @Query("minSalary") String minSalary);

    @GET("/api/company/all")
    Call<ApiResponseCompany> getAllCompany();

    @GET("/api/applicationform/mobile/user")
    Call<ApiResponseApplycationForm> getAllApplycationForm(
            @Query("userId") int userId
    );

    @GET("/api/applicationform/count/by-user/{id}")
    Call<ResponseDTO<Integer>> getApplicationFormCountByUser(@Path("id") int userId);


    @GET("api/user/check")
    Call<Boolean> checkUserSaveJob(
            @Query("userId") int userId,
            @Query("jobId") int jobId
    );

    @POST("/api/user/{userId}/save-job/{jobId}")
    Call<Void> saveJob(@Path("userId") int userId, @Path("jobId") int jobId);

    @DELETE("/api/user/{userId}/remove-job/{jobId}")
    Call<Void> removeSaveJob(@Path("userId") int userId, @Path("jobId") int jobId);


    @GET("api/user/{userId}/saved-jobs/count")
    Call<Integer> getSavedJobsCount(@Path("userId") int userId);
    @GET("/api/user/saved-jobs/{userId}")
    Call<ApiResponseJobFitUser> getAllJobSaved(
            @Path("userId") int userId
    );

    @POST("/api/login")
    Call<ResponseDTO<String>> login(
            @Query("username") String username,
            @Query("password") String password
    );

    @GET("/api/job/searchcompany")
    Call<ApiResponseJobFitCompany> getAllJobByCompany(@Query("idCompany") int id);

    @GET("/api/user/dashboard/{id}")
    Call<ResponseDTO<User>> getUserDashboard(@Path("id") int id);

    @GET("/api/job/get-all-job-fit-user/{id}")
    Call<ApiResponseJobFitUser> getAllJobsFitUser(@Path("id") int id);

    @GET("api/cv/{id}")
    Call<ResponseDTO<Cv>> getCvByUserId(@Path("id") int id);

    @POST("/api/user/register")
    Call<ResponseDTO<User>> register(
            @Query("email") String email,
            @Query("password") String password,
            @Query("name") String name,
            @Query("username") String username);

    @POST("/api/applicationform/dashboard/create")
    Call<ResponseDTO<ApplicationFormDTO>> createApplicationForm(@Body ApplicationFormDTO applicationFormDTO);


    @PUT("api/cv/update-job-suggess")
    Call<ResponseDTO<JobSuggestionDTO>> updateSuggestionCv(@Body JobSuggestionDTO jobSuggestionDTO);


    @GET("/api/applicationform/check-application")
    Call<ResponseDTO<String>> checkApplicationFormExists(
            @Query("userId") Integer userId,
            @Query("jobId") Integer jobId
    );
}