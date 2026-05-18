<template>
	<view>
	
		<view class="category-rule">
			<text @tap="changeCategory('popular')" :class="category=='popular'?'current':''">大众试用</text>
			<text @tap="changeCategory('tys')" :class="category=='tys'?'current':''">体验师专享</text>
		</view>
		
		<view class="status-rule">
			<text @tap="changeStatus('all')" :class="status=='all'?'current':''">全部</text>
			<text @tap="changeStatus('apply')" :class="status=='apply'?'current':''">申请中</text>
			<text @tap="changeStatus('try')" :class="status=='try'?'current':''">试用中</text>
			<text @tap="changeStatus('end')" :class="status=='end'?'current':''">已结束</text>
		</view>
		
		<view class="try-items-warp">
		<view class="try-item" v-for="item in tryItems" :key="item.id">
			<try-item-card :id="item.id" :image="buildImageUrl(item.image)"
			:title="item.title" :quantity="item.quantity" 
			:price="item.price" :apply-count="item.applyCount" 
			:report-count="item.reportCount" 
			:remain-days="item.remainDays" :category="item.category" 
			:status="item.status" :image-height="imageHeight"></try-item-card>
		</view>
		</view>
	</view>
</template>

<script>
	import TryItemCard from '@/components/TryItemCard.vue'
	export default {
		data() {
			return {
				category:"popular",
				status:"all",
				imageUrl:"",
				tryItems:[],
				imageHeight:"200rpx"
			}
		},
		methods: {
			// 构建完整的图片URL，添加时间戳防止缓存
			buildImageUrl(imagePath) {
				// 确保路径不为空
				if (!imagePath) return "";
				// 拼接完整的图片URL，并添加时间戳防止缓存
				const timestamp = new Date().getTime();
				return this.imageUrl + "/" + imagePath + "?t=" + timestamp;
			},
			changeCategory(newCategory){
				if (newCategory == this.category) {
					return;
				}
				//this.isChange = true;
				this.category = newCategory;
				this.getData();
			},
			changeStatus(newStatus){
				if (newStatus == this.status) {
					return;
				}
				//this.isChange = true;
				this.status = newStatus;
				this.getData();
			},
			getData(){
				uni.request({
					url:"/api/try/search",
					data:{category:this.category,status:this.status,pno:1,size:20},
					success:(resp)=>{
						if(resp.data.code !== 0){
							uni.showToast({
								title:resp.data.message || "暂无数据",
								icon:"none"
							})
						}
							
						else{
							this.tryItems = (resp.data.data && resp.data.data.list) || []
						}
						
					}
				})
			},
			
		},
		components:{
			TryItemCard
		},
		onLoad() {
			this.imageUrl = getApp().globalData.imageUrl;
			this.getData();
		}
	}
</script>

<style scoped>
	.try-items-warp{
		width: 92%;
		margin: 10rpx auto;
		display: flex;
		flex-wrap: wrap;
		justify-content: space-between;

	}
	.try-items-warp .try-item{
		width: 48%;
		
		box-shadow: 6rpx 6rpx 6rpx lightgray;
		border-radius: 20rpx;
		margin-bottom: 20rpx;
	}
	
	.category-rule , 
	.status-rule {
		width: 92%;
		margin: 10rpx 4%;
	}
	
	.category-rule text {
		display: inline-block;
		width: 50%;
		text-align: center;
	}
	.status-rule text {
		display: inline-block;
		width: 25%;
		text-align: center;
	}
	
	.current {
		color: red;
		font-weight: bold;
		border-bottom: 4rpx solid red;
	}

</style>
