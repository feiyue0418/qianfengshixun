<template>

    <div>
        <div class="card" style="margin-bottom: 10px;">
            <el-input v-model="data.title" :prefix-icon="Search" style="width: 300px; margin-right: 10px"
                      placeholder="请输入房源名称"></el-input>
            <el-button type="primary" style="margin-left: 10px;" @click="load">查询</el-button>
            <el-button type="info" @click="reset">重置</el-button>
        </div>
        <div class="card">
            <div style="margin-bottom: 10px;">
                <el-button type="primary" @click="handlerAdd">新增</el-button>
            </div>
            <div style="margin-bottom: 10px;">
                <el-table :data="data.tableData">
                    <el-table-column label="序号" prop="id" width="70"></el-table-column>
                    <el-table-column label="图片">
                        <template #default="scope">
                            <el-image v-if="scope.row.avatar" :src="scope.row.photos" :preview-src-list="[scope.row.avatar]" style="width: 40px;height: 40px;border-radius: 4px;"></el-image>
                        </template>
                    </el-table-column>
                    <el-table-column label="标题" prop="title"></el-table-column>
                    <el-table-column label="面积" prop="area"></el-table-column>
                    <el-table-column label="租金" prop="rent"></el-table-column>
                    <el-table-column label="地址" prop="address"></el-table-column>
                    <el-table-column label="操作" align="center" width="160">
                        <template #default="scope">
                            <el-button type="primary" size="small" @click="handleEdit(scope.row)">编辑</el-button>
                            <el-button type="danger" size="small" @click="handleDel(scope.row.id)">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
            <div class="card">
                <el-pagination background layout="prev, pager,next" @current-change="handelCurrentChange"
                               v-model:current-page="data.pageNum" v-model:page-size="data.pageSize" :total="data.total"/>
            </div>
        </div>
    </div>

    <el-dialog width="35%" v-model="data.formVisible" title=" 房源信息" destory-on-close>
        <el-form :model="data.form" ref="formRef" :rules="rules" label-width="100px" label-position="right" style="padding-right: 40px">
            <el-form-item label="房源标题">
                <el-input v-model="data.form.title" autocomplete="off"/>
            </el-form-item>
            <el-form-item label="房源面积">
                <el-input  v-model="data.form.area" autocomplete="off"/>
            </el-form-item>
            <el-form-item label="房源图片">
                <el-upload action="http://localhost:9090/files/upload" list-type="picture" :on-success="handleImgUploadSuccess">
                    <el-button type="primary">上传图片</el-button>
                </el-upload>
            </el-form-item>
            <el-form-item label="房源租金">
                <el-input v-model="data.form.rent" autocomplete="off"/>
            </el-form-item>
            <el-form-item label="房源地址">
                <el-input v-model="data.form.address" autocomplete="off"/>
            </el-form-item>
        </el-form>
        <template #footer>
      <span class="dialog-footer">
        <el-button @click="data.formVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">保 存</el-button>
      </span>
        </template>
    </el-dialog>

</template>


<script setup>
    import {reactive, ref} from "vue";
    import {Search} from "@element-plus/icons-vue";
    import request from "@/utils/request.js";
    import {ElMessage} from "element-plus";

    const data = reactive({
        title: '',
        tableData: [],
        pageNum: 1, //当前页码
        pageSize: 8, //每页个数
        total: 0,
        formVisible: false //新增框不显示
    })
    const formRef = ref()

    const rules = reactive({
        title: [
            {required: true, message: '请输入房源标题', trigger: 'blur'}
        ],
        area: [
            {required: true, message: '请输入房源面积', trigger: 'blur'}
        ]
    })


    //请求数据
    const load = () => {
        request.get("/house/selectPage", {
            params: {
                pageNum: data.pageNum,
                pageSize: data.pageSize,
                title: data.title,
            }
        }).then(res => {
            console.log("分页数据:" + JSON.stringify(res));
            data.tableData = res.data?.list || [];
            data.total = res.data?.total || 0;
        })
    }

    //翻页
    const handelCurrentChange = (pageNum) => {
        // 当翻页的时候重新加载数据即可
        load()
    };

    //重置
    const reset = () => {
        data.house = '';
        load()
    };

    // 调用方法获取后台数据
    load();

    //点击新增
    const handlerAdd = () => {
        data.form = {};
        data.formVisible = true; //打开对话框
    };

    // 保存数据到后台
    const save = () => {
        request.request({
            url: data.form.id ? '/house/update' : '/house/add',
            method: data.form.id ? 'PUT' : 'POST',
            data: data.form
        }).then(res => {
            if (res.code === '200') {
                load()    // 重新获取数据
                data.formVisible = false  // 关闭弹窗
                ElMessage.success("操作成功")
            } else {
                ElMessage.error(res.msg)
            }
        })
    }

    //点击编辑
    const handleEdit = (row) => {
        data.form = JSON.parse(JSON.stringify(row));
        data.formVisible = true;
    }

    //删除
    const handleDel = (id) => {
        ElMessageBox.confirm('您确认删除吗？', '删除确认', {type: 'warning'}).then(res => {
            request.delete('/house/delete/' + id).then(res => {
                if (res.code === '200') {
                    load()    // 重新获取数据
                    ElMessage.success("操作成功")
                } else {
                    ElMessage.error(res.msg)
                }
            })
        }).catch(res => {
        })
    }

    const handleImgUploadSuccess = (res) => {
        data.form.avatar = res.data
    }
</script>