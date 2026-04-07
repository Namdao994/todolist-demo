package com.lifetex.todolist.modules.todo.docs;

import com.lifetex.todolist.common.ApiResponseData;
import com.lifetex.todolist.common.PageResponse;
import com.lifetex.todolist.modules.todo.dto.TodoResponse;

public class TodoApiSchemas {
    public static class TodoApiResponse extends ApiResponseData<TodoResponse> {}
    public static class TodoPageApiResponse extends ApiResponseData<PageResponse<TodoResponse>> {}
}
