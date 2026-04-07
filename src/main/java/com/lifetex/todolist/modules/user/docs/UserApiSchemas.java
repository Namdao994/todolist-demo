package com.lifetex.todolist.modules.user.docs;

import com.lifetex.todolist.common.ApiResponseData;
import com.lifetex.todolist.common.PageResponse;
import com.lifetex.todolist.modules.user.dto.UserResponse;

public class UserApiSchemas {
    public static class UserApiResponse extends ApiResponseData<UserResponse> {}
    public static class UserPageApiResponse extends ApiResponseData<PageResponse<UserResponse>> {}
    public static class UserVoidApiResponse extends ApiResponseData<Void> {}
}
