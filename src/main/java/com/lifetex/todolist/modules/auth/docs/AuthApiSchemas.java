package com.lifetex.todolist.modules.auth.docs;

import com.lifetex.todolist.common.ApiResponseData;
import com.lifetex.todolist.modules.auth.dto.AuthLoginResponse;
import com.lifetex.todolist.modules.auth.dto.AuthRefreshTokenResponse;
import com.lifetex.todolist.modules.auth.dto.AuthRegisterResponse;

public class AuthApiSchemas {
    public static class AuthLoginApiResponse extends ApiResponseData<AuthLoginResponse> {}
    public static class AuthRefreshTokenApiResponse extends ApiResponseData<AuthRefreshTokenResponse> {}
    public static class AuthRegisterApiResponse extends ApiResponseData<AuthRegisterResponse> {}
}
