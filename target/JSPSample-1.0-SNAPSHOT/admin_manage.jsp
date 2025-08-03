<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="model.bean.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Quản lý sản phẩm</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        body{font-family:'Segoe UI',Tahoma,Verdana,sans-serif;background:#f5f6fa;margin:0;padding:0;}
        .container{max-width:1100px;margin:30px auto;padding:20px;background:#fff;border-radius:10px;box-shadow:0 4px 10px rgba(0,0,0,.05);}        h1{text-align:center;margin-bottom:1.5rem;}
        table{width:100%;border-collapse:collapse;margin-top:1rem;}
        th,td{border:1px solid #ddd;padding:8px;text-align:left;}
        th{background:#4ecdc4;color:#fff;}
        .actions a{margin-right:8px;color:#3498db;text-decoration:none;}
        .actions a.delete{color:#e74c3c;}
        .btn{display:inline-block;padding:10px 18px;background:#4ecdc4;color:#fff;text-decoration:none;border-radius:6px;margin-top:10px;transition:.3s;}
        .btn:hover{background:#38b8af;transform:translateY(-2px);}        
        /* modal */
        .modal{position:fixed;top:0;left:0;width:100%;height:100%;background:rgba(0,0,0,.6);display:none;justify-content:center;align-items:center;z-index:2000;}
        .modal-content{background:#fff;width:500px;padding:20px;border-radius:8px;position:relative;}
        .close{position:absolute;top:8px;right:12px;font-size:22px;cursor:pointer;}
        .form-group{margin-bottom:1rem;display:flex;flex-direction:column;}
        label{margin-bottom:4px;font-weight:600;}
        input,textarea,select{padding:8px 10px;border:1px solid #ccc;border-radius:6px;font-size:1rem;}
        .submit-btn{background:#4ecdc4;border:none;color:#fff;padding:10px 16px;border-radius:6px;cursor:pointer;transition:.3s;}
        .submit-btn:hover{background:#38b8af;}
    </style>
</head>
<body>
<%-- Giả lập danh sách sản phẩm trong session để demo --%>
<%
    List<Product> products = (List<Product>) application.getAttribute("products");
    if(products == null){
        products = new ArrayList<>();
        application.setAttribute("products", products);
    }
%>
    <div class="container">
        <h1>Quản lý sản phẩm</h1>
        <a href="#" class="btn" onclick="openForm()"><i class="fas fa-plus"></i> Thêm sản phẩm</a>
        <table>
            <thead>
                <tr>
                    <th>ID</th><th>Tên</th><th>Mô tả</th><th>Giá</th><th>Danh mục</th><th>Ảnh</th><th>Loại</th><th>Tình trạng</th><th>Hành động</th>
                </tr>
            </thead>
            <tbody>
            <% if(products.isEmpty()){ %>
                <tr><td colspan="9" style="text-align:center;">Chưa có sản phẩm</td></tr>
            <% }else{ for(Product p:products){ %>
                <tr>
                    <td><%= p.getId() %></td>
                    <td><%= p.getName() %></td>
                    <td><%= p.getDescription() %></td>
                    <td><%= String.format("%,.0f₫", p.getPrice()) %></td>
                    <td><%= p.getCategory() %></td>
                    <td><% if(p.getImageUrl()!=null && !p.getImageUrl().isEmpty()){ %><img src="<%= p.getImageUrl() %>" width="50"><% } %></td>
                    <td><%= p.getType() %></td>
                    <td><%= p.isAvailable()?"Còn":"Hết" %></td>
                    <td class="actions">
                        <a href="#" onclick="editProduct(<%= p.getId() %>)"><i class="fas fa-edit"></i></a>
                        <a href="ProductServlet?action=delete&id=<%= p.getId() %>" class="delete"><i class="fas fa-trash"></i></a>
                    </td>
                </tr>
            <% }} %>
            </tbody>
        </table>
    </div>

    <!-- Modal Form -->
    <div id="productModal" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeForm()">&times;</span>
            <h2 id="formTitle">Thêm sản phẩm</h2>
            <form id="productForm" action="ProductServlet" method="post">
                <input type="hidden" name="action" value="create" id="formAction">
                <input type="hidden" name="id" id="productId">
                <div class="form-group">
                    <label>Tên sản phẩm</label>
                    <input type="text" name="name" id="productName" required />
                </div>
                <div class="form-group">
                    <label>Mô tả</label>
                    <textarea name="description" id="productDesc" rows="3"></textarea>
                </div>
                <div class="form-group">
                    <label>Giá</label>
                    <input type="number" step="0.01" name="price" id="productPrice" required />
                </div>
                <div class="form-group">
                    <label>Danh mục</label>
                    <input type="text" name="category" id="productCategory"/>
                </div>
                <div class="form-group">
                    <label>URL Ảnh</label>
                    <input type="text" name="imageUrl" id="productImage"/>
                </div>
                <div class="form-group">
                    <label>Loại</label>
                    <select name="type" id="productType">
                        <option value="flower">Hoa</option>
                        <option value="card">Thiệp</option>
                    </select>
                </div>
                <div class="form-group">
                    <label>Tình trạng</label>
                    <select name="isAvailable" id="productAvail">
                        <option value="true">Còn hàng</option>
                        <option value="false">Hết hàng</option>
                    </select>
                </div>
                <button type="submit" class="submit-btn">Lưu</button>
            </form>
        </div>
    </div>

<script>
function openForm(){
    document.getElementById('productForm').reset();
    document.getElementById('formTitle').innerText='Thêm sản phẩm';
    document.getElementById('formAction').value='create';
    document.getElementById('productModal').style.display='flex';
}
function closeForm(){document.getElementById('productModal').style.display='none';}
function editProduct(id){
    // Trong bản demo, ta chưa có dữ liệu để prefill. Khi kết nối backend, cần request chi tiết.
    document.getElementById('formTitle').innerText='Cập nhật sản phẩm';
    document.getElementById('formAction').value='update';
    document.getElementById('productId').value=id;
    document.getElementById('productModal').style.display='flex';
}
window.onclick = function(event){
    if(event.target === document.getElementById('productModal')) closeForm();
}
</script>
</body>
</html>
