<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="id" type="java.lang.String" required="true" description="编号"%>
<%@ attribute name="cssClass" type="java.lang.String" required="false" description="css样式"%>
<%@ attribute name="cssStyle" type="java.lang.String" required="false" description="css样式"%>
<%@ attribute name="url" type="java.lang.String" required="true" description="网址"%>
<%@ attribute name="width" type="java.lang.String" required="true" description="网址"%>
<%@ attribute name="height" type="java.lang.String" required="true" description="网址"%>

<%@ attribute name="jsonData" type="java.lang.String" required="false" description="json数据参数"%>


<%@ attribute name="title" type="java.lang.String" required="true" description="选择框标题"%>
<%@ attribute name="value" type="java.lang.String" required="false" description="值"%>
<%@ attribute name="checked" type="java.lang.Boolean" required="false" description="是否显示复选框，如果不需要返回父节点，请设置notAllowSelectParent为true"%>
<%@ attribute name="extId" type="java.lang.String" required="false" description="排除掉的编号（不能选择的编号）"%>
<%@ attribute name="isAll" type="java.lang.Boolean" required="false" description="是否列出全部数据，设置true则不进行数据权限过滤（目前仅对Office有效）"%>
<%@ attribute name="module" type="java.lang.String" required="false" description="过滤栏目模型（只显示指定模型，仅针对CMS的Category树）"%>
<%@ attribute name="selectScopeModule" type="java.lang.Boolean" required="false" description="选择范围内的模型（控制不能选择公共模型，不能选择本栏目外的模型）（仅针对CMS的Category树）"%>
<%@ attribute name="allowClear" type="java.lang.Boolean" required="false" description="是否允许清除"%>
<%@ attribute name="allowInput" type="java.lang.Boolean" required="false" description="文本框可填写"%>


<%@ attribute name="smallBtn" type="java.lang.Boolean" required="false" description="缩小按钮显示"%>
<%@ attribute name="hideBtn" type="java.lang.Boolean" required="false" description="是否显示按钮"%>
<%@ attribute name="disabled" type="java.lang.String" required="false" description="是否限制选择，如果限制，设置为disabled"%>
<%@ attribute name="dataMsgRequired" type="java.lang.String" required="false" description=""%>

<a href="javascript:void(0)"  id="${id}" name="${id}" class="${cssClass}">${title}</a>

<script type="text/javascript">
	$("#${id}").click(function(){

		// 正常打开	
		top.$.jBox.open("iframe:${url}", "${title}", ${width}, ${height}, {
			ajaxData:{},buttons:{"确定":"ok", "关闭":"close", "关闭2":"close2"}, submit:function(v, h, f){
				
				if (v=="ok"){
					var table = h.find("iframe")[0].contentWindow.searchUserTable;
					var username =  $(table).find(".selected").attr('data');
					$("#${id}").val(username);
				}

			}, loaded:function(h){
				$(".jbox-content", top.document).css("overflow-y","hidden");
			}
		});
	});
</script>