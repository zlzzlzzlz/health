<template>
  <div class="user">
    <div class="formData">
      <el-card class="searchModule">
        <el-form :model="user" label-width="100px" :inline="true" :ref="user">
          <el-form-item label="意见查询" prop="name" style="margin-left:-20px">
            <el-input v-model="user.name" clearable></el-input>
          </el-form-item>
          <el-button type="primary" @click="search()" style="margin-left:40px">查询</el-button>
          <el-button type="primary" @click="resetForm(user)">重置</el-button>
        </el-form>
      </el-card>
    </div>
    <div class="contentB">
      <div class="tableData">
        <sirui-table
          class="operateTable"
          :page="page"
          :loading="load"
          :data="tableData"
          @handleSelectionChange="handleSelectionChange"
          @handleSizeChange="handleSizeChange"
          @handleCurrentChange="handleCurrentChange"
          :tableHeight="tableHeight"
        >
          <el-table-column align="center" type="selection" width="45"></el-table-column>
          <el-table-column
            v-for="(item,index) in columnList"
            :key="index"
            :prop="item.prop"
            :label="item.label"
            :width="item.width"
            :sortable="item.sort"
            :fixed="item.fixed"
            align="center"
            show-overflow-tooltip
          ></el-table-column>
        </sirui-table>
      </div>
    </div>
    <OpinionAdd ref="addData" v-on:search="search"></OpinionAdd>
    <OpinionEdit ref="editData" v-on:search="search"></OpinionEdit>
  </div>
</template>

<script>
import SiruiTable from "@/components/SiruiTable";
import OpinionAdd from "@/pages/OpinionManages/OpinionAdd";
import OpinionEdit from "@/pages/OpinionManages/OpinionEdit";
export default {
  name: "OpinionManages",
  components: {
    SiruiTable,
    OpinionAdd,
    OpinionEdit
  },
  data() {
    return {
      user: {
        name: "",
        phone: ""
      },
      page: {
        pages: 1,
        pageNum: 1,
        pageSize: 10,
        total: 0
      },
      load: false,
      multipleSelection: [],
      columnList: [
        { prop: "advice", label: "反馈食物名称" },
        { prop: "detail", label: "反馈食物详细信息" },
        { prop: "name", label: "反馈用户" },
      ],
      tableData: [],
      tableHeight: 400
    };
  },
  mounted() {
    this.getData();
  },
  methods: {
    //查询
    search() {
      this.$axios
        .get(
          "prod-api/user/searchAdvice?advice="+this.user.name
        )
        .then(res => {
          //this.loading.endLoading();
          if (200 == res.status) {
            this.tableData = res.data;
            console.log(this.tableData);
            this.$message({
              showClose: true,
              message: "查询成功",
              type: "success"
            });
          } else {
            this.$message({
              showClose: true,
              message: "查询失败",
              type: "error"
            });
          }
        });
    },
    //重置
    resetForm(formName) {
      if (this.$refs[formName] !== undefined) {
        this.$refs[formName].resetFields();
      }
    },
    getData() {
      this.$axios
        .get(
          "prod-api/user/getAdvice"
        )
        .then(res => {
          //this.loading.endLoading();
          if (200 == res.status) {
            this.tableData = res.data;
            console.log(this.tableData);
            this.$message({
              showClose: true,
              message: "查询成功",
              type: "success"
            });
          } else {
            this.$message({
              showClose: true,
              message: "查询失败",
              type: "error"
            });
          }
        });
    },
    //选中触发事件
    handleSelectionChange(row) {
      this.multipleSelection = row;
    },
    //每条页数
    handleSizeChange(val) {
      this.page.pageSize = val;
      this.getData();
    },
    //第几页
    handleCurrentChange(val) {
      this.page.pageNum = val;
      this.getData();
    },
    //新增按钮
    addData() {
      //防止子组件的值影响父组件
      this.$refs.addData.openDialog(true);
    },
    editData() {
      if (1 == this.multipleSelection.length) {
        //防止子组件的值影响父组件
        this.$refs.editData.openDialog(
          true,
          JSON.parse(JSON.stringify(this.multipleSelection[0]))
        );
      } else {
        this.$message({
          showClose: true,
          message: "请选择一条数据进行编辑",
          type: "error"
        });
      }
    },
    isopen() {
      if (this.multipleSelection.length >= 1) {
        if (0 != this.multipleSelection.length) {
          this.$confirm("是否启用?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          })
            .then(() => {})
            .catch(() => {
              this.$message({
                type: "info",
                message: "已取消启用"
              });
            });
        } else {
          this.$message({
            showClose: true,
            message: "请选择数据进行操作",
            type: "error"
          });
        }
      } else {
        this.$message({
          showClose: true,
          message: "请选择数据进行操作",
          type: "error"
        });
      }
    },
    isclose() {
      if (this.multipleSelection.length >= 1) {
        if (0 != this.multipleSelection.length) {
          this.$confirm("是否停用?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          })
            .then(() => {})
            .catch(() => {
              this.$message({
                type: "info",
                message: "已取消停用"
              });
            });
        } else {
          this.$message({
            showClose: true,
            message: "请选择数据进行操作",
            type: "error"
          });
        }
      } else {
        this.$message({
          showClose: true,
          message: "请选择数据进行操作",
          type: "error"
        });
      }
    },
    delData() {
      if (this.multipleSelection.length >= 1) {
        if (0 != this.multipleSelection.length) {
          this.$confirm("此操作会永久删除数据，是否继续?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          })
            .then(() => {})
            .catch(() => {
              this.$message({
                type: "info",
                message: "已取消删除"
              });
            });
        } else {
          this.$message({
            showClose: true,
            message: "请选择数据进行操作",
            type: "error"
          });
        }
      } else {
        this.$message({
          showClose: true,
          message: "请选择数据进行操作",
          type: "error"
        });
      }
    }
    //
  }
};
</script>

<style scoped>
.contentB {
  background: white;
  margin-top: 10px;
}
.user {
  margin-left: 55px;
}
.clickBtn {
  top: 9px;
  position: relative;
  left: -31%;
}
.tableData {
  margin-top: 20px;
}
</style>