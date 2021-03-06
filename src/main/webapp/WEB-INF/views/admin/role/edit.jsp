<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../shared/taglib.jsp"%>

<style>
    .error {
        color: #cc5965;
        display: inline-block;
        padding-left: 5px;
    }
    .success {
    	padding-top: 7px;
    	margin-bottom: 0;
    	color: #1ab394;
    }
    .failed {
    	padding-top: 7px;
    	margin-bottom: 0;
    	color: #ed5565;
    }
</style>

<div class="row wrapper border-bottom white-bg page-heading">
    <div class="col-lg-8">
        <h2>Edit Role details</h2>
        <ol class="breadcrumb">
            <li><a href="<spring:url value="/admin/"/>">Home</a></li>
            <li><a href="<spring:url value="/admin/role/"/>">Role Management</a></li>
            <li class="active"><strong>Role edit</strong></li>
        </ol>
    </div>
    <div class="col-lg-4">
        <div class="title-action">
            <a href="<spring:url value="list "/>" class="btn btn-white">Go Back</a>
            <a href="<spring:url value="details-${role.id}"/>" class="btn btn-info">View Details</a>
            <a href="<spring:url value="delete-${role.id}"/>" class="btn btn-danger delete-role">Delete</a>
        </div>
    </div>
</div>

<div class="wrapper wrapper-content animated fadeInRight ecommerce">

    <div class="row">
        <div class="col-lg-12">
            <div class="tabs-container">
                <ul class="nav nav-tabs">
                    <li class="active"><a data-toggle="tab" href="#tab-1">Role Info</a></li>
                </ul>

                <div class="tab-content">

                    <div id="tab-1" class="tab-pane active">
                        <div class="panel-body">

                            <form:form id="editRoleForm" method="POST" modelAttribute="role" class="form-horizontal">
                                <form:hidden path="id" />
			                    
                                <fieldset class="form-horizontal">
                                	<div class="form-group">
                                		<label class="col-lg-8">
                                			<c:if test="${success != null}">
                                				<p class="success">
                                					<c:out value="${success}" />
                                				</p>
                                			</c:if>
                                			<c:if test="${failed != null}">
                                				<p class="failed">
                                					<c:out value="${failed}" />
                                				</p>
                                			</c:if>
                                		</label>
                                    	<div class="col-lg-4" style="text-align: right">
                                       		<input type="submit" name="save" class="btn btn-primary" value="Save and Continue" />
                							<input type="submit" name="save" class="btn btn-success" value="Save" />
                                    	</div>
                                	</div>
                                
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">Type</label>
                                        <div class="col-sm-10">
                                            <form:input type="text" path="type" class="form-control" placeholder="Role name" />
                                            <form:errors path="type" cssClass="error" />
                                        </div>
                                    </div>
                                    <div class="hr-line-dashed"></div>

                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">Description</label>
                                        <div class="col-sm-10">
                                            <form:input type="text" path="description" class="form-control" placeholder="Description" />
                                            <form:errors path="description" cssClass="error" />
                                        </div>
                                    </div>

                                </fieldset>
                            </form:form>

                        </div>
                    </div>


                </div>

            </div>
        </div>
    </div>

</div>

<script>
    $(document).ready(function() {
    	$('.delete-role').click(function(e) {
    	    e.preventDefault();
    	    var href = $(this).attr("href");
    	    swal({
    	            title: "Are you sure?",
    	            text: "You will not be able to recover this role!",
    	            type: "warning",
    	            showCancelButton: true,
    	            confirmButtonColor: "#DD6B55",
    	            confirmButtonText: "Yes, delete it!",
    	            closeOnConfirm: false
    	        },
    	        function() {
                	$.get(href,function(){
                    	swal("Deleted!", "Role has been deleted.", "success");
                    	window.location.href = "list";
                	}).fail(function(){
                		swal("Error", "Role could not be deleted", "error");
                	});
                });
    	});

        $("#editRoleForm").validate({
            rules: {
                type: {
                    required: true,
                    maxlength: 15
                },
                description: {
                    required: false
                }
            }
        });
        
    });
</script>