package com.lifetex.todolist.modules.tag.docs;

import com.lifetex.todolist.common.ApiResponseData;
import com.lifetex.todolist.common.PageResponse;
import com.lifetex.todolist.modules.tag.dto.TagResponse;

import java.util.List;

public class TagApiSchemas {
    public static class TagApiResponse extends ApiResponseData<TagResponse>{}
    public static class TagPageApiResponse extends ApiResponseData<PageResponse<TagResponse>>{}
    public static class TagVoidApiResponse extends ApiResponseData<Void>{}
}
