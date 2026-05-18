<template>
	<view>
		<!-- 订单排序选择器 -->
		<view class="order-ruler">
			<!-- 最新按钮，点击切换到最新排序 -->
			<text @tap="changeOrder('new')" :class="order=='new'?'current':''">最新</text>
			<!-- 最热按钮，点击切换到最热排序 -->
			<text @tap="changeOrder('hot')" :class="order=='hot'?'current':''">最热</text>
		</view>

		<!-- 商品列表容器 -->
		<view class="cool-items-warp">
			<!-- 可滚动的商品列表视图 -->
			<scroll-view scroll-y="true" :show-scrollbar="false" 
			@scrolltolower="getMore"
			@scrolltoupper="refresh"> <!-- 滚动到顶部时刷新 -->
				<view class="scroll-view-wrap">
				<!-- 商品项列表 -->
					<view class="cool-item-view" v-for="item in coolItems" :key="item.id" @tap="gotoDetail(item)">
						<!-- 商品卡片组件 -->
						<cool-item-card @clickthumb="doThumb" :id="item.id" :name="item.name" :title="item.title"
							:price="item.price" :thumb-count="item.thumbCount" :comment-count="item.commentCount"
							:image="buildImageUrl(item.image)"></cool-item-card>
					</view>
				</view>
				<!-- 底部提示，当到达最后一页时显示 -->
				<view class="bottom-tips" v-if="isLastPage">--我是有底线的--</view>
			</scroll-view>	
		</view>
		
	</view>
</template>

<script>
	// 导入商品卡片组件
	import CoolItemCard from '@/components/CoolItemCard.vue';
	export default {
		data() {
			return {
				order: "new", // 当前排序方式，默认为最新
				pno: 1, // 当前页码
				coolItems: [], // 商品列表数据
				imageUrl: "", // 图片基础URL
				isLastPage: false, // 是否已到达最后一页
				isChange: false
			}
		},
		components: {
			CoolItemCard // 注册商品卡片组件
		},
		onLoad() {
			this.imageUrl = getApp().globalData.imageUrl;
			this.getData();
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
			refresh() {
				if(this.isChange){
					return;
				}
				this.pno = 1;
				this.isLastPage = false;
				this.coolItems = [];
				this.getData();
			},
			getMore() {
				if(this.isLastPage) {
					return;
				}
				this.pno++;
				this.getData();
			},
			changeOrder(newOrder) {
				if (newOrder == this.order) {
					return;
				}
				this.isChange = true;
				this.order = newOrder;
				this.pno = 1;
				this.coolItems = [];
				this.isLastPage = false;
				this.getData();
			},
			getData() {
				uni.request({
					url: "/api/coolitem/search",
					data: {
						order: this.order,
						pno: this.pno,
						size: 10
					},
					success: (resp) => {
						if (resp.data.code !== 0 || !resp.data.data) {
							uni.showToast({ title: resp.data.message || "暂无数据", icon: "none" });
							this.isLastPage = true;
							this.isChange = false;
							return;
						}
						this.isLastPage = resp.data.data.isLastPage;
						this.coolItems = this.coolItems.concat(resp.data.data.list || []);
						this.isChange = false;
					},
					fail: function(resp) {
						uni.showToast({
							title: "出错了",
							icon: 'error'
						})
					}
				})
			},
			gotoDetail(data) {
				uni.navigateTo({
					url: "/pages/cooldetail/cooldetail?id=" + data.id
				})
			},
			doThumb(data) {
				uni.showToast({ title: "请在首页登录后点赞", icon: "none" });
			}
		}
	}
</script>

<style>
	.bottom-tips {
		text-align: center;
		color: gray;
		font-size: 24rpx;
	}

	.cool-items-warp scroll-view {
		width: 100%;
		height: 70vh;
	}

	.order-ruler {
		width: 92%;
		margin: 10rpx 4%;
	}

	.order-ruler text {
		display: inline-block;
		width: 50%;
		text-align: center;
	}

	.current {
		color: red;
		font-weight: bold;
		border-bottom: 4rpx solid red;
	}
</style>
