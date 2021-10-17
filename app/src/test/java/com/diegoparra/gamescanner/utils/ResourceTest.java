package com.diegoparra.gamescanner.utils;

import static com.google.common.truth.Truth.assertThat;

import net.bytebuddy.implementation.bytecode.Throw;

import org.junit.Test;

public class ResourceTest {

    @Test
    public void loading_createCorrectResourceLoading() {
        Resource<String> resource = Resource.Loading();
        assertThat(resource.getStatus()).isEqualTo(Resource.Status.LOADING);
        assertThat(resource.getData()).isNull();
        assertThat(resource.getError()).isNull();
    }

    @Test
    public void success_createCorrectResourceSuccess() {
        String data = "data";
        Resource<String> resource = Resource.Success(data);
        assertThat(resource.getStatus()).isEqualTo(Resource.Status.SUCCESS);
        assertThat(resource.getData()).isEqualTo(data);
        assertThat(resource.getError()).isNull();
    }

    @Test
    public void error_createCorrectResourceError() {
        Throwable error = new NullPointerException();
        Resource<String> resource = Resource.Error(error);
        assertThat(resource.getStatus()).isEqualTo(Resource.Status.ERROR);
        assertThat(resource.getData()).isNull();
        assertThat(resource.getError()).isEqualTo(error);
    }

    @Test
    public void equals_resourceSuccess_true() {
        String data = "data";
        Resource<String> resource1 = Resource.Success(data);
        Resource<String> resource2 = Resource.Success(data);
        assertThat(resource1).isEqualTo(resource2);
    }

    @Test
    public void equals_resourceSuccess_false() {
        Resource<String> resource1 = Resource.Success("data1");
        Resource<String> resource2 = Resource.Success("data2");
        assertThat(resource1).isNotEqualTo(resource2);
    }

    @Test
    public void equals_resourceError_true() {
        Throwable error = new NullPointerException();
        Resource<String> resource1 = Resource.Error(error);
        Resource<String> resource2 = Resource.Error(error);
        assertThat(resource1).isEqualTo(resource2);
    }

    @Test
    public void equals_resourceError_false() {
        Resource<String> resource1 = Resource.Error(new NullPointerException());
        Resource<String> resource2 = Resource.Error(new IllegalArgumentException());
        assertThat(resource1).isNotEqualTo(resource2);
    }

    @Test
    public void equals_resourceLoading_true() {
        assertThat(Resource.Loading()).isEqualTo(Resource.Loading());
    }

    @Test
    public void equals_resourceDifferentTypes_false() {
        Resource<String> resourceLoading = Resource.Loading();
        Resource<String> resourceSuccess = Resource.Success("data");
        Resource<String> resourceError = Resource.Error(new NullPointerException());

        assertThat(resourceLoading).isNotEqualTo(resourceSuccess);
        assertThat(resourceLoading).isNotEqualTo(resourceError);
        assertThat(resourceSuccess).isNotEqualTo(resourceError);
    }



}