package com.duymanh.btl.api;

import com.duymanh.btl.dto.ApplicationFormDTO;
import com.duymanh.btl.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("/api/job/all")
    Call<ApiResponseJobFitUser> getAllJobs();

    @GET("/api/company/all")
    Call<ApiResponseCompany> getAllCompany();

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

    @POST("/api/user/register")
    Call<ResponseDTO<User>> register(
            @Query("email") String email,
            @Query("password") String password,
            @Query("username") String username,
            @Query("name") String name);

    @POST("/api/applicationform/dashboard/create")
    Call<ResponseDTO<ApplicationFormDTO>> createApplicationForm(@Body ApplicationFormDTO applicationFormDTO);

    @GET("/api/applicationform/check-application")
    Call<ResponseDTO<String>> checkApplicationFormExists(
            @Query("userId") Integer userId,
            @Query("jobId") Integer jobId
    );
}