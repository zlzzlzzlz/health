<template>
    <div class="alldiv">
    <div>
        <van-nav-bar
        title="饮水详情"
        left-arrow
        fixed
        @click-left="onClickLeft"
        />
    </div>
    <div v-for="(item,i) in water ">
    <van-cell center  :title="item.date" :value="item.ml" size="large"   style="left:-25px;top:10px"/>
    </div>
    </div>
</template>

<script>
export default {
    data() {
    return {
        water:[],
    };
    },
    methods: {
    onClickLeft() {
        this.$router.push("water");
    },
    getwater() {
        var id = localStorage.getItem("id");
        this.$axios.get("prod-api/user/getWater?userid=" + id).then((res) => {
        if (200 == res.status) {
            this.water = res.data;
            for(var i=0;i<this.water.length;i++){
                this.water[i].ml=this.water[i].ml+"ml";
            }
            console.log(this.water);
        } else {
            console.log("null");
        }
        });
    },
    },
    mounted() {
    this.getwater();
    },
};
</script>


<style scoped>
.alldiv {
    margin: 0;
    padding: 0;
    height: 100vh;
    background: white;
    margin-top:-4%;
}
</style>