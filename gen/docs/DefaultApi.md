# DefaultApi

All URIs are relative to *https://sms_backend*

Method | HTTP request | Description
------------- | ------------- | -------------
[**admitStudent**](DefaultApi.md#admitStudent) | **POST** /api/admission/admit | POST api/admission/admit


<a name="admitStudent"></a>
# **admitStudent**
> admitStudent()

POST api/admission/admit

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://sms_backend");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    try {
      apiInstance.admitStudent();
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#admitStudent");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | OK |  -  |

