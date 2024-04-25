<template>
  <div class="box">
    <!-- 查询客户 -->
    <div class="formData">
      <el-card class="searchModule">
        <el-form :model="box" label-width="100px" :inline="true" :ref="box">
          <el-form-item label="食谱名称" prop="boxCount">
            <el-input v-model="box.boxCount" clearable></el-input>
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
        <el-button type="primary" @click="editData" style="position:absolute;left:105px">修改</el-button>
        <el-button type="primary" @click="delData()" style="position:absolute;left:190px">删除</el-button>
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
            <el-table-column prop="pic" label="菜品图片" min-width="70" height="55">
            <!--图片显示-->
            <template slot-scope="scope">
              <img :src="scope.row.pic" min-width="70" height="55" />
            </template>
          </el-table-column>
        </sirui-table>
      </div>
    </div>
    <BoxAdd ref="addData" v-on:search="search"></BoxAdd>
    <BoxEdit ref="editData" v-on:search="search"></BoxEdit>
  </div>
</template>

<script>
import SiruiTable from "@/components/SiruiTable";
import BoxAdd from "@/pages/boxManages/BoxAdd";
import BoxEdit from "@/pages/boxManages/BoxEdit";
export default {
  name: "boxManages",
  components: {
    SiruiTable,
    BoxAdd,
    BoxEdit
  },
  data() {
    return {
      box: {
        boxCount: "",
        useCount: "",
        surplusCount: ""
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
          prop: "detail",
          label: "食谱名称",
          width: 200
        },
        { prop: "breakfast", label: "早餐" },
        { prop: "lunch", label: "午餐" },
        { prop: "dinner", label: "晚餐" },
        { prop: "hot", label: "适用人群" },
        { prop: "pic", label: "食谱图片" },
      ],
      tableData: [
      ],
      tableHeight: 400
    };
  },
  mounted() {
    this.getData();
  },
  methods: {
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
          message: "请选择一条数据进行修改",
          type: "error"
        });
      }
    },
    // 查询用户
    search() {
      this.$axios
        .get(
          "prod-api/user/searchEat?detail="+this.box.boxCount
        )
        .then(res => {
          //this.loading.endLoading();
          if (200 == res.status) {
            this.tableData = res.data;
            console.log(this.tableData);
            for(var i =0 ;i<this.tableData.length;i++){
              if(this.tableData[i].hot==1)this.tableData[i].hot="减肥"
              if(this.tableData[i].hot==2)this.tableData[i].hot="保持/塑性"
              if(this.tableData[i].hot==3)this.tableData[i].hot="增肌"
            }
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
    //查询列表数据
    getData() {
      this.$axios
        .get(
          "prod-api/user/getEat"
        )
        .then(res => {
          //this.loading.endLoading();
          if (200 == res.status) {
            this.tableData = res.data;
            console.log(this.tableData);
            for(var i =0 ;i<this.tableData.length;i++){
              if(this.tableData[i].hot==1)this.tableData[i].hot="减肥"
              if(this.tableData[i].hot==2)this.tableData[i].hot="保持/塑性"
              if(this.tableData[i].hot==3)this.tableData[i].hot="增肌"
            }
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
    //删除 只有总数量为0才可删除
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
                this.$axios
                  .delete("prod-api/user/delEat?id="+this.multipleSelection[0].id)
                  .then(res => {
                    // this.loading.endLoading();
                    if (200 == res.status) {
                      this.getData();
                      this.$message({
                        type: "success",
                        message: "删除成功"
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
// .formData {
//   width: 100%;
//   height: 93px;
//   margin-left: 45px;
//   padding: 5px;
//   position: absolute;
//   top: 5px;
// }
.contentB {
  width: 100%;
  // height: 840px;
  // margin-left: 50px;
  padding: 5px;
  position: absolute;
  top: 145px;
  background-color: #ffffff;
}
.clickBtn {
  height: 27px;
}
</style>
