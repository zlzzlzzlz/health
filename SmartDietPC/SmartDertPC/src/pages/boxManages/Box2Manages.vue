<template>
  <div class="box2">
    <!-- 查询客户 -->
    <div class="formData">
      <el-card class="searchModule">
        <el-form :model="box" label-width="100px" :inline="true" :ref="box">
          <el-form-item label="箱子规格" prop="useCount">
            <el-input v-model="box.boxStandard" clearable></el-input>
          </el-form-item>
          <el-form-item label="使用状态" prop="state">
            <el-input v-model="box.state" clearable></el-input>
          </el-form-item>
          <el-form-item label="使用人" prop="boxUser">
            <el-input v-model="box.boxUser" clearable></el-input>
          </el-form-item>
          <el-button type="primary" @click="search()">查询</el-button>
          <el-button type="primary" @click="resetForm(box)">重置</el-button>
        </el-form>
      </el-card>
    </div>

    <div class="contentB">
      <br />
      <div class="clickBtn">
        <el-button type="primary" @click="addData" style="position:absolute;left:30px">新增</el-button>
        <!-- <el-button type="primary" @click="editData" style="position:absolute;left:105px">编辑</el-button> -->
        <el-button type="primary" @click="delData()" style="position:absolute;left:105px">删除</el-button>
      </div>
      <br />
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
            v-for="(item, index) in columnList"
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
    <Box2Add ref="addData" v-on:search="search"></Box2Add>
    <BoxEdit ref="editData" v-on:search="search"></BoxEdit>
  </div>
</template>

<script>
import SiruiTable from "@/components/SiruiTable";
import Box2Add from "@/pages/boxManages/Box2Add";
import BoxEdit from "@/pages/boxManages/BoxEdit";
export default {
  name: "box2Manages",
  components: {
    SiruiTable,
    Box2Add,
    BoxEdit
  },
  data() {
    return {
      box: {
        boxCount: "",
        state: "",
        boxUser: "",
        boxStandard: "",
        isUsed: "",
        usedByName: ""
      },
      page: {
        pages: 0, //总共多少页
        pageNum: 1, //第几页
        pageSize: 10,
        total: 0
      },
      load: false,
      multipleSelection: [],
      columnList: [
        {
          prop: "boxCode",
          label: "箱子编号",
          width: 200
        },
        { prop: "boxStandard", label: "箱子规格" },
        { prop: "boxUnitPrice", label: "箱子积分单价" },
        { prop: "isUsed", label: "使用状态" },
        { prop: "usedByName	", label: "使用人" },
        { prop: "remark", label: "备注" },
        { prop: "delFlag", label: "删除标识" }
      ],
      tableData: [
        // {
        //   boxCode: "150×200",
        //   boxId: "12",
        //   memberPoints: "200",
        //   state: "1",
        //   boxUser: "rjh",
        //   note: "111",
        //   delete: "0"
        // },
        // {
        //   boxCode: "150×200",
        //   boxId: "12",
        //   memberPoints: "200",
        //   state: "1",
        //   boxUser: "rjh",
        //   note: "222",
        //   delete: "0"
        // },
        // {
        //   boxCode: "150×200",
        //   boxId: "12",
        //   memberPoints: "200",
        //   state: "1",
        //   boxUser: "rjh",
        //   note: "333",
        //   delete: "0"
        // },
        // {
        //   boxCode: "150×200",
        //   boxId: "12",
        //   memberPoints: "200",
        //   state: "1",
        //   boxUser: "rjh",
        //   note: "444",
        //   delete: "0"
        // },
        // {
        //   boxCode: "150×200",
        //   boxId: "12",
        //   memberPoints: "200",
        //   state: "1",
        //   boxUser: "rjh",
        //   note: "555",
        //   delete: "0"
        // }
      ],
      tableHeight: 400
    };
  },
  mounted() {
    this.getData();
  },
  methods: {
    // 查询用户
    search() {
      this.getData();
    },
    //查询列表数据
    getData() {
      this.$axios
        .get(
          "prod-api/backend/box/info/list?" +
            "pageNum=" +
            this.page.pageNum +
            "&pageSize=" +
            this.page.pageSize +
            "&boxStandard=" +
            this.box.boxStandard +
            "&isUsed=" +
            this.box.isUsed +
            "&usedByName=" +
            this.box.usedByName
        )
        .then(res => {
          //this.loading.endLoading();
          if (200 == res.data.code) {
            this.tableData = res.data.rows;
            this.page.total = res.data.total;
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
    // 重置
    resetForm(formName) {
      if (this.$refs[formName] !== undefined) {
        this.$refs[formName].resetFields();
      }
    },
    // 选中触发事件
    handleSelectionChange(row) {
      this.multipleSelection = row;
    },
    // 每页条数
    handleSizeChange(val) {
      this.page.pageSize = val;
      this.getData();
    },
    // 第几页
    handleCurrentChange(val) {
      this.page.pageNum = val;
      this.getData();
    }, //新增按钮
    addData() {
      this.$refs.addData.openDialog(true);
    },
    // 修改按钮点击事件
    editData() {
      if (1 == this.multipleSelection.length) {
        // 为了防止子组件的值 影响父组件
        this.$refs.editData.openDialog(
          true,
          // JSON.parse()方法用于将一个JSON字符串转换为对象
          JSON.parse(JSON.stringify(this.multipleSelection[0]))
        );
      } else {
        this.$message({
          showClose: true,
          message: "请选择一条数据进行查看详情",
          type: "error"
        });
      }
    },
    //删除
    delData() {
      if (this.multipleSelection.length >= 1) {
        if (0 != this.multipleSelection.length) {
          if (this.multipleSelection[0].status == "0") {
            alert("启用中，不可删除");
          } else {
            this.$confirm("此操作会永久删除数据，是否继续?", "提示", {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: "warning"
            })
              //调用接口
              .then(() => {
                let params = {
                  ids: []
                };
                for (var i = 0; i < this.multipleSelection.length; i++) {
                  params.ids.push(this.multipleSelection[i].id);
                }
                params.ids = params.ids.join(",");
                this.$axios
                  .delete("prod-api/backend/box/info/delete/" + params.ids)
                  .then(res => {
                    // this.loading.endLoading();
                    if (200 == res.data.code) {
                      //this.searchForm();
                      this.$message({
                        type: "success",
                        message: res.data.msg
                      });
                    }
                  });
              })
              .catch(() => {
                this.$message({
                  type: "info",
                  message: "已取消删除"
                });
              });
          }
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
  }
};
</script>

<style lang="scss" scoped>
.formData {
  width: 100%;
  height: 93px;
  margin: 10px;
  margin-left: 45px;
  padding: 5px;
  position: absolute;
  top: 5px;
}
.contentB {
  width: 100%;
  height: 840px;
  margin-left: 50px;
  padding: 5px;
  position: absolute;
  top: 145px;
  background-color: #ffffff;
}
.clickBtn {
  height: 27px;
}
</style>
